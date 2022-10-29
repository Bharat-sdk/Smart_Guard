package com.hbeonlabs.smartguard.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.telephony.SmsManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

fun Fragment.makeToast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}


fun Fragment.makeToast(@StringRes res: Int) {
    Toast.makeText(requireContext(), res, Toast.LENGTH_SHORT).show()
}

fun Fragment.snackBar(text: String) {
    Snackbar.make(
        requireView(),
        text,
        Snackbar.LENGTH_SHORT
    ).show()
}

fun Fragment.snackBar(@StringRes res: Int) {
    Snackbar.make(
        requireView(),
        res,
        Snackbar.LENGTH_SHORT
    ).show()
}

fun <T> Fragment.collectLatestLifeCycleFlow(
    flow: Flow<T>,
    collect: suspend (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun Context.getBitmap(uri: Uri): Bitmap =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.contentResolver, uri))
    else MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

fun Context.hideKeyboard(view:View)
{
    val inputMethodManager =
        this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


fun Fragment.sendSMS(phoneNumber:String,message:String,DELIVERY:Intent?)
{
    try {
        val smsManager: SmsManager
        if (Build.VERSION.SDK_INT>=23) {
            smsManager = this.requireActivity().getSystemService(SmsManager::class.java)
        }
        else{
            smsManager = SmsManager.getDefault()
        }
       /* val intent = Intent()
        val sentIntent = PendingIntent.getBroadcast(activity, 0, intent,
            PendingIntent.FLAG_ONE_SHOT)*/
        val intent2 = Intent(DELIVERY)
        val deliveryIntent = PendingIntent.getBroadcast(activity, 0, intent2,
            PendingIntent.FLAG_ONE_SHOT)
        smsManager.sendTextMessage(phoneNumber, null, message, null, deliveryIntent)

        Toast.makeText(this.requireContext(), "Message Sent", Toast.LENGTH_LONG).show()

    } catch (e: Exception) {
        Toast.makeText(this.requireContext(), "Please enter all the data.."+e.message.toString(), Toast.LENGTH_LONG)
            .show()
    }
}