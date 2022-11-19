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
import com.hbeonlabs.smartguard.databinding.DialogAddSensorMessageBinding
import com.hbeonlabs.smartguard.databinding.DialogFormatHubBinding
import com.hbeonlabs.smartguard.databinding.DialogVerifyAddHubBinding
import com.hbeonlabs.smartguard.ui.fragments.addAHub.AddAHubEvent
import com.hbeonlabs.smartguard.ui.fragments.addAHub.AddAHubViewModel
import com.hbeonlabs.smartguard.ui.fragments.hubSettings.HubSettingEvents
import com.hbeonlabs.smartguard.ui.fragments.hubSettings.HubSettingsViewModel
import com.hbeonlabs.smartguard.utils.collectLatestLifeCycleFlow
import kotlinx.coroutines.flow.collectLatest

fun Fragment.dialogFormatHub(
    viewModel:HubSettingsViewModel,
    onYesClick : ()->Unit,
    onButtonContinue:()-> Unit
)
: Dialog {
    val dialog = Dialog(requireContext())

    val binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.dialog_format_hub, null, false) as DialogFormatHubBinding
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(binding.root)
    dialog.window!!.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    dialog.show()
    dialog.setCancelable(false)

    binding.txtFormat.setOnClickListener {
        binding.clAreYouSure.visibility = View.GONE
        binding.llFormatting.visibility = View.VISIBLE
        binding.progressHorizontalDialogVerifyHub.visibility = View.VISIBLE
        onYesClick()
    }

    binding.btnContinue.setOnClickListener {
        dialog.cancel()
        onButtonContinue()
    }

    collectLatestLifeCycleFlow(viewModel.hubSettingsEvents)
    {
        when(it)
        {
            HubSettingEvents.FormatHubSuccessEvent -> {
               binding.llFormatted.visibility = View.VISIBLE
                binding.llFormatting.visibility = View.GONE

            }
            is HubSettingEvents.SQLErrorEvent -> {

            }
            else -> {}
        }
    }


    return dialog

}