package com.hbeonlabs.smartguard.ui.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.databinding.DialogVerifyAddHubBinding
import com.hbeonlabs.smartguard.ui.fragments.addAHub.AddAHubEvent
import com.hbeonlabs.smartguard.ui.fragments.addAHub.AddAHubViewModel
import com.hbeonlabs.smartguard.utils.collectLatestLifeCycleFlow
import kotlinx.coroutines.flow.collectLatest

fun Fragment.dialogVerifyHubAddition(
    number:String,
    viewModel:AddAHubViewModel,
    onContinue: () -> Unit,
) {
    val dialog = Dialog(requireContext())

    val binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.dialog_verify_add_hub, null, false) as DialogVerifyAddHubBinding
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(binding.root)
    dialog.window!!.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    dialog.show()
    dialog.setCancelable(false)

    binding.number = number

    binding.btnGoBack.setOnClickListener {
        dialog.dismiss()
    }

    binding.btnContinue.setOnClickListener {
        dialog.dismiss()
        onContinue()
    }

    binding.btnTryAgain.setOnClickListener {
        dialog.dismiss()
    }

    binding.btnGoBack.setOnClickListener {
        dialog.dismiss()
        requireActivity().onBackPressed()
    }


    collectLatestLifeCycleFlow(viewModel.addHubEvents)
    {
        when(it)
        {
            AddAHubEvent.HubRegisteredEvent -> {
                binding.llVerifying.visibility = View.GONE
                binding.llVerified.visibility = View.VISIBLE
            }
            is AddAHubEvent.HubRegistrationErrorEvent -> {
                binding.llVerifying.visibility = View.GONE
                binding.llVerificationFailed.visibility = View.VISIBLE
            }
            else -> {}
        }
    }

}