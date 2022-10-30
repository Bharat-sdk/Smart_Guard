package com.hbeonlabs.smartguard.ui.fragments.selectHub

import android.view.View
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.databinding.FragmentAddAHubBinding
import com.hbeonlabs.smartguard.databinding.FragmentHubBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.HubListAdapter

import org.koin.android.ext.android.inject


class FragmentSelectAHub:BaseFragment<SelectHubViewModel,FragmentHubBinding>() {

    private  val selectAHubViewModel: SelectHubViewModel by inject()
    override fun getViewModel(): SelectHubViewModel {
            return selectAHubViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_hub
    }

    override fun initView() {
        super.initView()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.setImageResource(R.drawable.ic_baseline_add)
        (requireActivity() as MainActivity).binding.toolbarIconStart.visibility = View.GONE
        (requireActivity() as MainActivity).binding.toolbarIconEnd.setOnClickListener {
            findNavController().navigate(FragmentSelectAHubDirections.actionFragmentSelectAHubToFragmentAddAHub())
        }

        addDummyData()

    }

    private fun addDummyData(){
        val list = arrayListOf(
            Hub("HUB1","1","","987675678",false, hub_arm_state = false),
            Hub("HUB2","2","","987675678",false, hub_arm_state = true),
            Hub("HUB3","3","","987675678",false, hub_arm_state = true),
            Hub("HUB4","4","","987675678",false, hub_arm_state = false),
        )
        val adapter = HubListAdapter()

        adapter.differ.submitList(list)
        adapter.setOnItemClickListener {

        }
        binding.adapter = adapter

    }



}