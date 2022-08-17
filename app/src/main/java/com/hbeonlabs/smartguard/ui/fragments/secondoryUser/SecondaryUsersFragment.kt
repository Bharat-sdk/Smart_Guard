package com.hbeonlabs.smartguard.ui.fragments.secondoryUser

import android.view.View
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentAddHubPostVerificationBinding
import com.hbeonlabs.smartguard.databinding.FragmentSecondaryUsersBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.SecondaryUserAdapter
import com.hbeonlabs.smartguard.utils.AppLists

import org.koin.android.ext.android.inject


class SecondaryUsersFragment:BaseFragment<SecondaryUserViewModel,FragmentSecondaryUsersBinding>() {

    private  val secondaryUserViewModel: SecondaryUserViewModel by inject()
    override fun getViewModel(): SecondaryUserViewModel {
            return secondaryUserViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_secondary_users
    }

    override fun initView() {
        super.initView()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.visibility = View.INVISIBLE
        (requireActivity() as MainActivity).binding.toolbarIconEnd2.visibility = View.INVISIBLE


        val adapter = SecondaryUserAdapter()
        adapter.setOnItemClickListener { secondaryUser, i ->
            if (secondaryUser.user_name.isNotBlank() || secondaryUser.user_phone_number.isNotBlank())
            {
                // Add Popup Menu
            }
            else{
               // Navigate to Add Sensors Fragment
            }
        }
        adapter.differ.submitList(AppLists(requireContext()).secondaryUserList)
        binding.adapter = adapter
    }



}