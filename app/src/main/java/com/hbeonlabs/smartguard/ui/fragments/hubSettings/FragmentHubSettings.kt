package com.hbeonlabs.smartguard.ui.fragments.hubSettings

import android.app.Activity
import android.os.Environment
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
import com.hbeonlabs.smartguard.utils.AppConstants
import com.hbeonlabs.smartguard.utils.makeToast
import com.hbeonlabs.smartguard.utils.snackBar
import kotlinx.coroutines.flow.collectLatest


import org.koin.android.ext.android.inject


class FragmentHubSettings : BaseFragment<HubSettingsViewModel, FragmentHubSettingsBinding>() {
    val args: FragmentHubSettingsArgs by navArgs()
    var imageUri = "".toUri()
    lateinit var _hub: Hub


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
                    snackBar(AppConstants.TASK_CANCELLED)
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
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            hubSettingsViewModel.getHubFromId(args.hubId).collectLatest { hub ->
                _hub = hub
                binding.edtAddHubName.setText(hub.hub_name)
                if (hub.hub_image.isEmpty()) {
                    binding.imgEditHubImage.setImageResource(R.drawable.default_sensor_image)
                } else {
                    imageUri = hub.hub_image.toUri()
                    binding.imgEditHubImage.setImageURI(hub.hub_image.toUri())
                }
            }
        }
    }


}