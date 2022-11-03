package com.hbeonlabs.smartguard.ui.fragments.addAHub

import android.Manifest
import android.content.IntentFilter
import android.provider.Telephony
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentAddAHubBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.dialogs.dialogVerifyHubAddition
import com.hbeonlabs.smartguard.utils.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class FragmentAddAHub:BaseFragment<AddAHubViewModel,FragmentAddAHubBinding>(),
    SmsBroadcastReceiver.Listener {

    private  val addAHubViewModel: AddAHubViewModel by inject()
    val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
           // Log.d("DEBUG", "${it.key} = ${it.value}")
        }
    }
    var hubSimNo = ""
    var hubSerialNo = ""
    lateinit var smsBroadcastReceiver: SmsBroadcastReceiver


    override fun getViewModel(): AddAHubViewModel {
            return addAHubViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_add_a_hub
    }

    override fun initView() {
        super.initView()
        smsBroadcastReceiver = SmsBroadcastReceiver()
        requireActivity().registerReceiver(smsBroadcastReceiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))

        observe()
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.RECEIVE_MMS
            )
        )
        //requestReadAndSendSmsPermission()
        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_baseline_help)
            setOnClickListener { snackBar("Help") }
        }

        (requireActivity() as MainActivity).binding.toolbarIconEnd2.visibility = View.INVISIBLE

        binding.btnAddHub.setOnClickListener {
             hubSerialNo = binding.edtAddHubSerial.text.toString()
             hubSimNo = binding.edtAddHubSimNo.text.toString()

            if (hubSerialNo.isEmpty()){
                makeToast("Please Enter Hub Serial Number")
            }
            else if(hubSimNo.isEmpty())
            {
                makeToast("Please Enter Your Phone Number")
            }
            else if (hubSimNo.length < 10)
            {
               makeToast("Please Enter Valid Phone Number")
            }
            else{

                dialogVerifyHubAddition(hubSimNo,addAHubViewModel) {
                    getViewModel().addHub(hubSerialNo, hubSimNo)
                }

                smsBroadcastReceiver.setListener(this)

                sendSMS(hubSimNo.trim(),hubSerialNo.trim()+" R"){
                    // SMS Delivered Intent
                    getViewModel().hubSmsDelivered()
                }
            }

            requireContext().hideKeyboard(it)
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
                AddAHubEvent.HubRegisteredEvent -> {

                }
                is AddAHubEvent.HubRegistrationErrorEvent -> {
                    makeToast(it.message)
                }
                AddAHubEvent.MessageDeliveredEvent -> {

                }
            }
        }
    }

    override fun onTextReceived(text: String?, smsSender: String?) {
        if (text.equals("You have been registered."))
        {
            getViewModel().hubRegistrationSuccess()
        }
        else{
            getViewModel().hubAdditionError(text.toString())
        }
    }


}