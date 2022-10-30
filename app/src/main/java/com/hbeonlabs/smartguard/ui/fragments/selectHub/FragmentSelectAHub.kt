package com.hbeonlabs.smartguard.ui.fragments.selectHub

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.databinding.FragmentAddAHubBinding
import com.hbeonlabs.smartguard.databinding.FragmentHubBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.HubListAdapter
import com.hbeonlabs.smartguard.utils.collectLatestLifeCycleFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

import org.koin.android.ext.android.inject


class FragmentSelectAHub:BaseFragment<SelectHubViewModel,FragmentHubBinding>() {

    lateinit var adapter:HubListAdapter
    private  val selectAHubViewModel: SelectHubViewModel by inject()
    override fun getViewModel(): SelectHubViewModel {
            return selectAHubViewModel
    }

    override fun getLayoutResourceId()= R.layout.fragment_hub


    override fun initView() {
        super.initView()

        observe()
        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_baseline_add)
            setOnClickListener {
            findNavController().navigate(FragmentSelectAHubDirections.actionFragmentSelectAHubToFragmentAddAHub())
        }}
        (requireActivity() as MainActivity).binding.toolbarIconEnd2.visibility = View.INVISIBLE

        binding.rvHub.layoutManager = LinearLayoutManager(requireActivity())
        adapter = HubListAdapter()
        adapter.setOnItemClickListener {
            findNavController().navigate(FragmentSelectAHubDirections.actionFragmentSelectAHubToFragmentHubDetails(it))
        }
        binding.adapter = adapter


    }

    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            selectAHubViewModel.getHubData().collectLatest {
                adapter.differ.submitList(it)
            }
        }
    }




}