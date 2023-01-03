package com.hbeonlabs.smartguard.ui.fragments.hubDetails.sos

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import androidx.lifecycle.lifecycleScope
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.data.local.datastore.SharedPreferences
import com.hbeonlabs.smartguard.data.local.models.SecondaryUser
import com.hbeonlabs.smartguard.databinding.FragmentPagerSosBinding
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.HubDetailsViewModel
import com.hbeonlabs.smartguard.ui.fragments.secondoryUser.SecondaryUserViewModel
import com.hbeonlabs.smartguard.utils.makeToast
import com.hbeonlabs.smartguard.utils.sendSMS
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

@SuppressLint("ClickableViewAccessibility")
class FragmentPagerSOS : BaseFragment<HubDetailsViewModel, FragmentPagerSosBinding>() {
    lateinit var secondaryUserList: kotlin.collections.List<SecondaryUser>
    val sharedPreferences: SharedPreferences by inject()


    private val hubDetailsViewModel by sharedStateViewModel<HubDetailsViewModel>()
    private val secondaryUserViewModel by viewModel<SecondaryUserViewModel>()

    override fun getViewModel(): HubDetailsViewModel {
        return hubDetailsViewModel
    }


    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_pager_sos
    }

    override fun initView() {
        super.initView()


        binding.btnSos.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    getViewModel().startPress()
                }
                MotionEvent.ACTION_UP -> {
                    getViewModel().resetPress()
                }
            }
            true
        }


        lifecycleScope.launchWhenStarted {
            getViewModel().progressIndicatorLiveData.collect {
                binding.progress2Sec.progress = it
                if (it == 100) {
                    binding.progress2Sec.progress = 0
                    makeToast("Location Sent to all users")
                    // Send Current Location maps to secondary all users
                    secondaryUserList.forEach { secondaryUser ->
                        try {
                            sendSMS(
                                secondaryUser.user_phone_number,
                                "https://www.google.com/maps/search/?api=1&query=${sharedPreferences.getLocationLat()},${sharedPreferences.getLocationLong()}"
                            ) {

                            }
                        } catch (e: Exception) {
                            Log.d("TAG", "initView: " + e.localizedMessage)
                        }
                    }
                }
            }


        }

        lifecycleScope.launchWhenStarted {
           secondaryUserList = secondaryUserViewModel.getSecondaryUsersUsingHubOnly(hubDetailsViewModel.hub_id)
        }
    }

    override fun onPause() {
        super.onPause()
        getViewModel().resetPress()
    }


}