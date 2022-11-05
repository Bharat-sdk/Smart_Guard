package com.hbeonlabs.smartguard.ui.fragments.hubDetails.armDisarm

import android.app.Dialog
import android.content.IntentFilter
import android.graphics.PorterDuff.Mode
import android.os.Build
import android.provider.Telephony
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentPagerSirenArmingBinding
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.HubDetailsViewModel
import com.hbeonlabs.smartguard.utils.SmsBroadcastReceiver
import com.hbeonlabs.smartguard.utils.collectLatestLifeCycleFlow
import com.hbeonlabs.smartguard.utils.makeToast
import com.hbeonlabs.smartguard.utils.sendSMS
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel


class FragmentPagerSirenArming :
    BaseFragment<HubDetailsViewModel, FragmentPagerSirenArmingBinding>(),
    SmsBroadcastReceiver.Listener {

    lateinit var smsBroadcastReceiver: SmsBroadcastReceiver
    private val hubDetailsViewModel by sharedStateViewModel<HubDetailsViewModel>()
    lateinit var hubSirenRemoteDialog: Dialog
    override fun getViewModel(): HubDetailsViewModel {
        return hubDetailsViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_pager_siren_arming
    }

    override fun initView() {
        super.initView()
        smsBroadcastReceiver = SmsBroadcastReceiver()
        requireActivity().registerReceiver(
            smsBroadcastReceiver,
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        )
        smsBroadcastReceiver.setListener(this)
        observe()

        // =================== HUB DISARM =========================
        binding.cardHubDisarm.setOnClickListener {
            hubDetailsViewModel.startLoading()

            // ============== If Not Registered With Remote =====================
            if (hubDetailsViewModel.hub?.hub_disarm_registered == false) {
                hubDetailsViewModel.hub?.let { hub ->
                    sendSMS(hub.hub_phone_number, hub.hub_serial_number + " K02") {
                        // Delivered Listener
                    }
                }
            }
            // ============== If Registered With Remote =====================
            else {
                hubDetailsViewModel.hub?.let { hub ->
                    sendSMS(hub.hub_phone_number, hub.hub_serial_number + " DI") {}
                }
            }
        }


        // =================== HUB ARM =========================
        binding.cardHubArm.setOnClickListener {
            hubDetailsViewModel.startLoading()
            if (hubDetailsViewModel.hub?.hub_arm_registered == false) {
                hubDetailsViewModel.hub?.let { hub ->
                    sendSMS(hub.hub_phone_number, hub.hub_serial_number + " K01") {
                        // Delivered Listener
                    }
                }
            }
            // ============== If ARM Registered With Remote =====================
            else {
                hubDetailsViewModel.hub?.let { hub ->
                    sendSMS(hub.hub_phone_number, hub.hub_serial_number + " EN") {}
                }
            }
        }



        binding.cardHubRing.setOnClickListener {
            hubDetailsViewModel.startLoading()
            hubDetailsViewModel.hub?.let { hub ->
                sendSMS(hub.hub_phone_number, hub.hub_serial_number + " SON") {}
            }
        }

        binding.cardHubSilence.setOnClickListener {
            hubDetailsViewModel.startLoading()
            hubDetailsViewModel.hub?.let { hub
                ->
                sendSMS(hub.hub_phone_number, hub.hub_serial_number + " SOF") {}
            }
        }

    }

    private fun observe() {
        collectLatestLifeCycleFlow(hubDetailsViewModel.hubEvents)
        {
            when (it) {
                is HubDetailsViewModel.HubDetailsEvents.ArmDisarmEvent -> {
                    makeToast(it.message)
                }
                is HubDetailsViewModel.HubDetailsEvents.SQLErrorEvent -> {
                    makeToast(it.message)
                }
                is HubDetailsViewModel.HubDetailsEvents.SilenceRingEvent -> {
                    makeToast(it.message)
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            delay(100)
            hubDetailsViewModel.getHubFromId(hubDetailsViewModel.hub_id).collect {
                if (it.hub_siren) {
                    binding.llHubRing.background = resources.getDrawable(R.drawable.bg_green, null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.textRing.setTextColor(
                            resources.getColor(
                                R.color.on_boarding_green,
                                null
                            )
                        )
                    } else {
                        binding.textRing.setTextColor(resources.getColor(R.color.on_boarding_green))
                    }
                    binding.imgRing.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.on_boarding_green
                        ), Mode.MULTIPLY
                    )


                    binding.llHubSilence.background =
                        resources.getDrawable(R.drawable.bg_grey_unselected, null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.txtSilence.setTextColor(resources.getColor(R.color.grey, null))
                    } else {
                        binding.txtSilence.setTextColor(resources.getColor(R.color.grey))
                    }
                    binding.imgSilence.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.grey
                        ), Mode.MULTIPLY
                    )

                } else {
                    binding.llHubSilence.background = resources.getDrawable(R.drawable.bg_red, null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.txtSilence.setTextColor(resources.getColor(R.color.red, null))
                    } else {
                        binding.txtSilence.setTextColor(resources.getColor(R.color.red))
                    }
                    binding.imgSilence.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        ), Mode.MULTIPLY
                    )

                    binding.llHubRing.background =
                        resources.getDrawable(R.drawable.bg_grey_unselected, null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.textRing.setTextColor(resources.getColor(R.color.grey, null))
                    } else {
                        binding.textRing.setTextColor(resources.getColor(R.color.grey))
                    }
                    binding.imgRing.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.grey
                        ), Mode.MULTIPLY
                    )

                }

                if (it.hub_arm_state) {
                    binding.llHubArm.background = resources.getDrawable(R.drawable.bg_green, null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.txtArm.setTextColor(
                            resources.getColor(
                                R.color.on_boarding_green,
                                null
                            )
                        )
                    } else {
                        binding.txtArm.setTextColor(resources.getColor(R.color.on_boarding_green))
                    }
                    binding.imgArm.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.on_boarding_green
                        ), Mode.MULTIPLY
                    )

                    binding.llHubDisarm.background =
                        resources.getDrawable(R.drawable.bg_grey_unselected, null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.txtDisarm.setTextColor(resources.getColor(R.color.grey, null))
                    } else {
                        binding.txtDisarm.setTextColor(resources.getColor(R.color.grey))
                    }
                    binding.imgDisarm.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.grey
                        ), Mode.MULTIPLY
                    )


                } else {
                    binding.llHubDisarm.background = resources.getDrawable(R.drawable.bg_red, null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.txtDisarm.setTextColor(resources.getColor(R.color.red, null))
                    } else {
                        binding.txtDisarm.setTextColor(resources.getColor(R.color.red))
                    }
                    binding.imgDisarm.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        ), Mode.MULTIPLY
                    )

                    binding.llHubArm.background =
                        resources.getDrawable(R.drawable.bg_grey_unselected, null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.txtArm.setTextColor(resources.getColor(R.color.grey, null))
                    } else {
                        binding.txtArm.setTextColor(resources.getColor(R.color.grey))
                    }
                    binding.imgArm.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.grey
                        ), Mode.MULTIPLY
                    )

                }
            }
        }

    }

    override fun onTextReceived(text: String?, smsSender: String?) {

        if (text != null) {
            if (text.startsWith("Press Remote Button to save Remote")) {
                hubDetailsViewModel.stopLoading()

            } else if (text == "Remote ID : 01 saved" || text == "Remote ID : 02 saved") {
                when (text.substring(13, 15)) {
                    "01" -> hubDetailsViewModel.armRegistered()
                    "02" -> hubDetailsViewModel.disarmRegistered()
                    /*"03" -> hubDetailsViewModel.armRegistered()
                    "04" -> hubDetailsViewModel.armRegistered()*/
                }
                hubSirenRemoteDialog.cancel()
            } else if (text == "Configuration timeout") {
                hubSirenRemoteDialog.dismiss()
                makeToast("Configuration Timeout Please Retry Again")
            } else if (text == "Smart Guard is activated.") {
                hubDetailsViewModel.armDisarmHub(true)
                makeToast(text)
            } else if (text == "Smart Guard is deactivated.") {
                hubDetailsViewModel.armDisarmHub(false)
                makeToast(text)
            } else if (text == "Siren on.") {
                hubDetailsViewModel.silenceRingHub(true)
                makeToast(text)
            } else if (text == "Siren off.") {
                hubDetailsViewModel.silenceRingHub(false)
                makeToast(text)
            } else {
                makeToast(text)
            }
        }
    }


}