package com.hbeonlabs.smartguard.ui.fragments.addAHub

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentAddAHubBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.utils.collectLatestLifeCycleFlow
import com.hbeonlabs.smartguard.utils.hideKeyboard
import com.hbeonlabs.smartguard.utils.makeToast
import com.hbeonlabs.smartguard.utils.sendSMS
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
    val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {

            when (intent?.action) {
                "SMS_SENT" -> makeToast("SMS SENT")
            }
        }
    }

    override fun getViewModel(): AddAHubViewModel {
            return addAHubViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_add_a_hub
    }

    override fun initView() {
        super.initView()
        val filter = IntentFilter()
        filter.addAction("SMS_SENT")
        requireContext().registerReceiver(broadCastReceiver,filter)
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
            setOnClickListener { snackBar("Help") }
        }

        (requireActivity() as MainActivity).binding.toolbarIconEnd2.visibility = View.INVISIBLE

        binding.btnAddHub.setOnClickListener {
            val hubSerialNo = binding.edtAddHubSerial.text.toString()
            val hubSimNo = binding.edtAddHubSimNo.text.toString()


           // addAHubViewModel.addHub(hubSerialNo,hubSimNo)
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


    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(broadCastReceiver)
    }

    //sent sms
    private fun sendSMS2(phoneNumber:String,  message:String) {
        val SENT = "SMS_SENT"
        val  DELIVERED = "SMS_DELIVERED"

        val sentPI = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(requireContext(), 0, Intent(SENT),PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(requireContext(), 0, Intent(SENT),0)
        }
        val deliveredPI = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(requireContext(), 0,  Intent(DELIVERED), PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(requireContext(), 0,  Intent(DELIVERED), 0)
        }

        requireActivity().registerReceiver(object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {

                when(resultCode)
                {
                    Activity.RESULT_OK->
                        Toast.makeText(requireContext(), "SMS sent",
                            Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE->

                        Toast.makeText(requireContext(), "Generic failure",
                            Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_NO_SERVICE->

                        Toast.makeText(requireContext(), "No service",
                            Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_NULL_PDU->

                        Toast.makeText(requireContext(), "Null PDU",
                            Toast.LENGTH_SHORT).show()


                    SmsManager.RESULT_ERROR_RADIO_OFF->

                        Toast.makeText(requireContext(), "Radio off",
                            Toast.LENGTH_SHORT).show()

                }
            }

        }, IntentFilter(SENT))


        requireActivity().registerReceiver(object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {

                when(resultCode)
                {
                    Activity.RESULT_OK->
                        Toast.makeText(requireContext(), "SMS delivered",
                            Toast.LENGTH_SHORT).show()
                    Activity.RESULT_CANCELED->

                        Toast.makeText(requireContext(), "SMS not delivered",
                            Toast.LENGTH_SHORT).show();


                }
            }

        }, IntentFilter(SENT))

        sendSMS(phoneNumber,"$message R",sentPI,deliveredPI)

    }



}