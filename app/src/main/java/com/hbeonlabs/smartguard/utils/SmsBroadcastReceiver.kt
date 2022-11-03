package com.hbeonlabs.smartguard.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import timber.log.Timber


class SmsBroadcastReceiver : BroadcastReceiver() {
    private var listener: Listener? = null
    override fun onReceive(context: Context?, intent: Intent) {
        Log.d(TAG, "onReceive: ")
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            var smsSender = ""
            var smsBody = ""
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsSender = smsMessage.displayOriginatingAddress
                smsBody += smsMessage.messageBody
            }
            Timber.tag(TAG).d("onReceive: "+ smsBody+smsSender)
            /*  if (smsSender == serviceProviderNumber) {

            }*/
            if (listener != null) {
                listener!!.onTextReceived(smsBody, smsSender)
            }
        }
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    interface Listener {
        fun onTextReceived(text: String?,smsSender: String?)
    }

    companion object {
        private const val TAG = "TAG"
    }
}