package com.hbeonlabs.smartguard.ui.fragments.secondoryUser.add

import android.app.Activity
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.github.dhaval2404.imagepicker.ImagePicker
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentAddSecandoryUserBinding
import com.hbeonlabs.smartguard.databinding.FragmentSecondaryUsersBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.SecondaryUserAdapter
import com.hbeonlabs.smartguard.utils.AppLists

import org.koin.android.ext.android.inject


class AddSecondaryUserFragment:BaseFragment<AddSecondaryUserViewModel,FragmentAddSecandoryUserBinding>() {

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

    private  val secondaryUserViewModel: AddSecondaryUserViewModel by inject()
    override fun getViewModel(): AddSecondaryUserViewModel {
            return secondaryUserViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_add_secandory_user
    }

    override fun initView() {
        super.initView()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.visibility = View.INVISIBLE
        (requireActivity() as MainActivity).binding.toolbarIconEnd2.visibility = View.INVISIBLE

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