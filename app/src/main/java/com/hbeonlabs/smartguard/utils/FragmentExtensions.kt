package com.hbeonlabs.smartguard.utils

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
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


fun Fragment.sendSMS(phoneNumber:String,message:String)
{
    try {
        val smsManager: SmsManager
        if (Build.VERSION.SDK_INT>=23) {
            smsManager = this.requireActivity().getSystemService(SmsManager::class.java)
        }
        else{
            smsManager = SmsManager.getDefault()
        }

        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        makeToast(AppConstants.MESSAGE_SENT)


    } catch (e: Exception) {
        makeToast(AppConstants.ENTER_ALL_DATA)

    }
}