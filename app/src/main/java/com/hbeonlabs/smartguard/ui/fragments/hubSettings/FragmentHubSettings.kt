package com.hbeonlabs.smartguard.ui.fragments.hubSettings

import android.app.Activity
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentAddAHubBinding
import com.hbeonlabs.smartguard.databinding.FragmentHubSettingsBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity

import org.koin.android.ext.android.inject


class FragmentHubSettings:BaseFragment<HubSettingsViewModel,FragmentHubSettingsBinding>() {


    private val startImagePickerResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!

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


    private  val hubSettingsViewModel: HubSettingsViewModel by inject()
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
        }
        (requireActivity() as MainActivity).binding.toolbarIconEnd.visibility = View.INVISIBLE

        binding.descManageSecondaryNum.setOnClickListener {
            findNavController().navigate(R.id.secondaryUsersFragment)
        }

        binding.btnUploadFromGallery.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .createIntent {
                    startImagePickerResult.launch(it)
                }
        }

        binding.btnUploadFromCamera.setOnClickListener {
            ImagePicker.with(this)
                .cameraOnly()
                .createIntent {
                    startImagePickerResult.launch(it)
                }
        }



    }



}