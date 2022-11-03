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
import com.hbeonlabs.smartguard.databinding.DialogArmDisarmHubBinding
import com.hbeonlabs.smartguard.databinding.DialogVerifyAddHubBinding
import com.hbeonlabs.smartguard.ui.fragments.addAHub.AddAHubEvent
import com.hbeonlabs.smartguard.ui.fragments.addAHub.AddAHubViewModel
import com.hbeonlabs.smartguard.utils.collectLatestLifeCycleFlow
import kotlinx.coroutines.flow.collectLatest

fun Fragment.dialogArmDisarmRingSilenceRemote(
    message:String,
): Dialog {
    val dialog = Dialog(requireContext())

    val binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.dialog_arm_disarm_hub, null, false) as DialogArmDisarmHubBinding
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(binding.root)
    dialog.window!!.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    dialog.show()
    dialog.setCancelable(false)

    binding.message = message

    return dialog

}