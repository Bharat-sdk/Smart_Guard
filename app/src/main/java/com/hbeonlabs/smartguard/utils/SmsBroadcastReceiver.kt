package com.hbeonlabs.smartguard.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log


class SmsBroadcastReceiver(
/*    private val serviceProviderNumber: String,
    private val serviceProviderSmsCondition: String*/
) :
    BroadcastReceiver() {
    private var listener: Listener? = null
    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            var smsSender = ""
            var smsBody = ""
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsSender = smsMessage.displayOriginatingAddress
                smsBody += smsMessage.messageBody
            }
           /* if (smsSender == serviceProviderNumber && smsBody.startsWith(
                    serviceProviderSmsCondition
                )
            ) {*/
                if (listener != null) {
                    listener!!.onTextReceived(smsBody,smsSender)
                }
         //   }
        }
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    interface Listener {
        fun onTextReceived(text: String?,smsSender: String?)
    }

    companion object {
        private const val TAG = "SmsBroadcastReceiver"
    }
}