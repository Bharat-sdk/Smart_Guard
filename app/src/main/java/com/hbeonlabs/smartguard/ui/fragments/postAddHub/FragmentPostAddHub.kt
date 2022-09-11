package com.hbeonlabs.smartguard.ui.fragments.postAddHub

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.dhaval2404.imagepicker.ImagePicker
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentAddHubPostVerificationBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.utils.collectLatestLifeCycleFlow
import com.hbeonlabs.smartguard.utils.getBitmap
import com.hbeonlabs.smartguard.utils.makeToast

import org.koin.android.ext.android.inject


class FragmentPostAddHub:BaseFragment<PostAddHubViewModel,FragmentAddHubPostVerificationBinding>() {

    val args: FragmentPostAddHubArgs by navArgs()
    private  val postAddHubViewModel: PostAddHubViewModel by inject()
    lateinit var img:Bitmap

    private val startImagePickerResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    img = requireContext().getBitmap(fileUri)
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

    override fun getViewModel(): PostAddHubViewModel {
            return postAddHubViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_add_hub_post_verification
    }

    override fun initView() {
        super.initView()

        makeToast(args.hubSerialNo)
        (requireActivity() as MainActivity).binding.toolbarIconEnd.visibility = View.INVISIBLE
        (requireActivity() as MainActivity).binding.toolbarIconEnd2.visibility = View.INVISIBLE
        observe()

        binding.btnContinueToAddSensors.setOnClickListener {
            findNavController().navigate(FragmentPostAddHubDirections.actionFragmentPostAddHubToFragmentSelectAHub())
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

        binding.btnContinueToAddSensors.setOnClickListener {
            postAddHubViewModel.updateHub(binding.edtAddHubName.text.toString(),img,args.hubSerialNo)
        }
    }

    fun observe(){
        collectLatestLifeCycleFlow(postAddHubViewModel.postAddHubEvents){
            when(it)
            {
                is AddHubEvents.HubNameValidationErrorEvent -> {
                    makeToast(it.message)
                }
                AddHubEvents.NavigateToHub -> {
                    findNavController().navigate(FragmentPostAddHubDirections.actionFragmentPostAddHubToFragmentSelectAHub())
                }
                is AddHubEvents.SQLErrorEvent -> {
                    makeToast(it.message)
                }
            }
        }
    }



}