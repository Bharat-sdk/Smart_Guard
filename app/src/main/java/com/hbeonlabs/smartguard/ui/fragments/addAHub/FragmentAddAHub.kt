package com.hbeonlabs.smartguard.ui.fragments.addAHub

import android.Manifest
import android.content.pm.PackageManager
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentAddAHubBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.utils.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class FragmentAddAHub:BaseFragment<AddAHubViewModel,FragmentAddAHubBinding>() {

    private  val addAHubViewModel: AddAHubViewModel by inject()
    val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
           // Log.d("DEBUG", "${it.key} = ${it.value}")
        }
    }

    override fun getViewModel(): AddAHubViewModel {
            return addAHubViewModel
    }

    override fun getLayoutResourceId()=R.layout.fragment_add_a_hub


    override fun initView() {
        super.initView()
        observe()
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS
            )
        )
        //requestReadAndSendSmsPermission()
        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_baseline_help)
            setOnClickListener { snackBar(AppConstants.HELP) }
        }

        (requireActivity() as MainActivity).binding.toolbarIconEnd2.visibility = View.INVISIBLE

        binding.btnAddHub.setOnClickListener {
            val hubSerialNo = binding.edtAddHubSerial.text.toString()
            val hubSimNo = binding.edtAddHubSimNo.text.toString()
            addAHubViewModel.addHub(hubSerialNo,hubSimNo)
        }

        binding.clAddAHub.setOnClickListener {
            requireContext().hideKeyboard(it)
        }

    }

    private fun observe(){
        collectLatestLifeCycleFlow(getViewModel().addHubEvents){
            when(it)
            {
                AddAHubEvent.NavigateToPostHubEvent -> {
                    val registeredOn = Calendar.getInstance().timeInMillis
                    val formatter = SimpleDateFormat("dd/MM/yyyy")
                    val date = formatter.format(registeredOn)
                    findNavController().navigate(FragmentAddAHubDirections.actionFragmentAddAHubToFragmentPostAddHub(binding.edtAddHubSerial.text.toString(),date,binding.edtAddHubSimNo.text.toString()))
                }
                is AddAHubEvent.PhoneValidationErrorEvent -> {
                    makeToast(it.message)
                }
                is AddAHubEvent.SerialNumberValidationErrorEvent -> {
                    makeToast(it.message)
                }
                is AddAHubEvent.SQLErrorEvent -> {
                    makeToast(it.message)
                }
            }
        }
    }

/*
    fun isSmsPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    */
/**
     * Request runtime SMS permission
     *//*

    private fun requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_SMS
            )
        ) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_SMS),
            100
        )
    }
*/




}