package com.hbeonlabs.smartguard.ui.fragments.secondoryUser.edit

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
import com.hbeonlabs.smartguard.databinding.FragmentAddSecandoryUserBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.fragments.secondoryUser.add.AddSecondaryUserEvents
import com.hbeonlabs.smartguard.ui.fragments.secondoryUser.add.AddSecondaryUserViewModel
import com.hbeonlabs.smartguard.utils.makeToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

import org.koin.android.ext.android.inject


class EditSecondaryUserFragment:BaseFragment<AddSecondaryUserViewModel,FragmentAddSecandoryUserBinding>() {
    var imageUri = "".toUri()


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
   /*     secondaryUserViewModel.hub_id = args.hubId
        secondaryUserViewModel.slot = args.slot*/

        observe()



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

        binding.btnConfirmNumber.setOnClickListener {

            secondaryUserViewModel.addSecondaryUser( binding.edtUserName.text.toString(),imageUri,  binding.edtUserNumber.text.toString())
        }


    }

    fun observe()
    {
        viewLifecycleOwner.lifecycleScope.launch {
            secondaryUserViewModel.addSecondaryUserEvents.collectLatest {
                when (it) {
                    AddSecondaryUserEvents.AddUserSuccessEvent -> {
                      //  findNavController().navigate(AddSecondaryUserFragmentDirections.actionAddSecondaryUserFragmentToSecondaryUsersFragment(secondaryUserViewModel.hub_id))
                    }
                    is AddSecondaryUserEvents.SQLErrorEvent -> {
                        makeToast(it.message)
                    }
                }
            }
        }
    }



}