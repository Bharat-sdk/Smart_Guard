package com.hbeonlabs.smartguard.ui.fragments.addAHub

import android.view.View
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentAddAHubBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity

import org.koin.android.ext.android.inject


class FragmentAddAHub:BaseFragment<AddAHubViewModel,FragmentAddAHubBinding>() {

    private  val addAHubViewModel: AddAHubViewModel by inject()
    override fun getViewModel(): AddAHubViewModel {
            return addAHubViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_add_a_hub
    }

    override fun initView() {
        super.initView()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_baseline_help)
            setOnClickListener { snackBar("Help") }
        }
        (requireActivity() as MainActivity).binding.toolbarIconEnd2.visibility = View.INVISIBLE

        binding.btnAddHub.setOnClickListener {
            findNavController().navigate(FragmentAddAHubDirections.actionFragmentAddAHubToFragmentPostAddHub())
        }

    }



}