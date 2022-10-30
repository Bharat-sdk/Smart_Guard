package com.hbeonlabs.smartguard.ui.fragments.secondoryUser.add

import android.view.View
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentAddSecandoryUserBinding
import com.hbeonlabs.smartguard.databinding.FragmentSecondaryUsersBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.SecondaryUserAdapter
import com.hbeonlabs.smartguard.utils.AppLists

import org.koin.android.ext.android.inject


class AddSecondaryUserFragment:BaseFragment<AddSecondaryUserViewModel,FragmentAddSecandoryUserBinding>() {

    private  val secondaryUserViewModel: AddSecondaryUserViewModel by inject()
    override fun getViewModel(): AddSecondaryUserViewModel {
            return secondaryUserViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_add_secandory_user
    }

    override fun initView() {
        super.initView()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.visibility = View.INVISIBLE
        (requireActivity() as MainActivity).binding.toolbarIconEnd2.visibility = View.INVISIBLE


    }



}