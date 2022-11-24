package com.hbeonlabs.smartguard.ui.fragments.hubSettings

import android.app.Activity
import android.content.IntentFilter
import android.os.Environment
import android.provider.Telephony
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.dhaval2404.imagepicker.ImagePicker
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.databinding.FragmentHubSettingsBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.dialogs.dialogFormatHub
import com.hbeonlabs.smartguard.utils.SmsBroadcastReceiver
import com.hbeonlabs.smartguard.utils.makeToast
import com.hbeonlabs.smartguard.utils.sendSMS
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject


class FragmentHubSettings : BaseFragment<HubSettingsViewModel, FragmentHubSettingsBinding>(),
    SmsBroadcastReceiver.Listener {
    val args: FragmentHubSettingsArgs by navArgs()
    var imageUri = "".toUri()
    lateinit var _hub: Hub
    lateinit var smsBroadcastReceiver: SmsBroadcastReceiver


    private val startImagePickerResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    imageUri = fileUri

                    binding.imgEditHubImage.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    snackBar(ImagePicker.getError(data))
                }
                else -> {
                    snackBar("Task Cancelled")
                }
            }
        }


    private val hubSettingsViewModel: HubSettingsViewModel by inject()
    override fun getViewModel(): HubSettingsViewModel {
        return hubSettingsViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_hub_settings
    }

    override fun initView() {
        super.initView()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_baseline_check_24)
            // Edit Hub Functionality
            setOnClickListener {
                val name = binding.edtAddHubName.text.toString()
                hubSettingsViewModel.updateHubName(name, imageUri.toString(), args.hubId)
            }
        }

        (requireActivity() as MainActivity).binding.toolbarIconEnd2.visibility = View.INVISIBLE
        observe()
        // =========  Setting Sms Listener ==================

        smsBroadcastReceiver = SmsBroadcastReceiver()
        requireActivity().registerReceiver(
            smsBroadcastReceiver,
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        )
        smsBroadcastReceiver.setListener(this)

        binding.descManageSecondaryNum.setOnClickListener {

            findNavController().navigate(
                FragmentHubSettingsDirections.actionFragmentHubSettingsToSecondaryUsersFragment(
                    args.hubId
                )
            )
        }

        binding.btnUploadFromGallery.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .saveDir(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
                .createIntent {
                    startImagePickerResult.launch(it)
                }
        }

        binding.btnUploadFromCamera.setOnClickListener {
            ImagePicker.with(this)
                .cameraOnly()
                .saveDir(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
                .createIntent {
                    startImagePickerResult.launch(it)
                }
        }

        binding.descFormatHub.setOnClickListener {
            dialogFormatHub(hubSettingsViewModel,
                { sendSMS(_hub.hub_phone_number, "${_hub.hub_serial_number} F"){} },
                {  findNavController().navigate(FragmentHubSettingsDirections.actionFragmentHubSettingsToFragmentSelectAHub())}
            )
        }


    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            hubSettingsViewModel.hubSettingsEvents.collectLatest {
                when (it) {
                    is HubSettingEvents.SQLErrorEvent -> {
                        makeToast(it.message)
                    }
                    HubSettingEvents.UpdateHubSuccessEvent -> {
                        hubSettingsViewModel.getHubFromId(args.hubId)
                        findNavController().navigate(
                            FragmentHubSettingsDirections.actionFragmentHubSettingsToFragmentHubDetails(
                                _hub
                            )
                        )
                    }
                   else ->{}
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            hubSettingsViewModel.getHubFromId(args.hubId).collectLatest { hub ->
                if (hub != null) {
                    _hub = hub
                    binding.edtAddHubName.setText(hub.hub_name)
                    if (hub.hub_image.isEmpty()) {
                        binding.imgEditHubImage.setImageResource(R.drawable.doorsensor)
                    } else {
                        imageUri = hub.hub_image.toUri()
                        binding.imgEditHubImage.setImageURI(hub.hub_image.toUri())
                    }
                }
            }
        }
    }

    override fun onTextReceived(text: String?, smsSender: String?) {
        if (text!= null)
        {
            if (text == "Master user has formatted the system.")
            {
               hubSettingsViewModel.formatHub(_hub.hub_serial_number)

            }
        }
    }


}