package com.hbeonlabs.smartguard.utils

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

fun Fragment.makeToast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
}


fun Fragment.makeToast(@StringRes res: Int) {
    Toast.makeText(requireContext(), res, Toast.LENGTH_LONG).show()
}

fun Fragment.snackBar(text: String) {
    Snackbar.make(
        requireView(),
        text,
        Snackbar.LENGTH_LONG
    ).show()
}

fun Fragment.snackBar(@StringRes res: Int) {
    Snackbar.make(
        requireView(),
        res,
        Snackbar.LENGTH_LONG
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