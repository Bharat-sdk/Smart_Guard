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
import com.hbeonlabs.smartguard.utils.hideKeyboard
import com.hbeonlabs.smartguard.utils.makeToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

import org.koin.android.ext.android.inject


class EditSecondaryUserFragment:BaseFragment<AddSecondaryUserViewModel,FragmentAddSecandoryUserBinding>() {
    var imageUri = "".toUri()
    private val args:EditSecondaryUserFragmentArgs by navArgs()


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
       secondaryUserViewModel.hub_id = args.secondaryUser.hub_serial_number
        secondaryUserViewModel.slot = args.secondaryUser.slot

        observe()

        val user = args.secondaryUser
        binding.edtUserName.setText(user.user_name)
        binding.edtUserNumber.setText(user.user_phone_number)
        imageUri  =user.user_pic.toUri()
        binding.imgEditHubImage.setImageURI(user.user_pic.toUri())



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

        binding.addSecondaryNumber.setOnClickListener {
            requireContext().hideKeyboard(it)
        }
        binding.btnConfirmNumber.setOnClickListener {
           secondaryUserViewModel.editSecondaryUser( binding.edtUserName.text.toString(),imageUri,  binding.edtUserNumber.text.toString())
        }


    }

    fun observe()
    {
        viewLifecycleOwner.lifecycleScope.launch {
            secondaryUserViewModel.addSecondaryUserEvents.collectLatest {
                when (it) {
                    AddSecondaryUserEvents.EditUserSuccessEvent -> {
                        makeToast("User details updated successfully")
                        findNavController().navigate(EditSecondaryUserFragmentDirections.actionEditSecondaryUserFragmentToSecondaryUsersFragment(getViewModel().hub_id))
                    }
                    is AddSecondaryUserEvents.SQLErrorEvent -> {
                        makeToast(it.message)
                    }
                }
            }
        }
    }



}