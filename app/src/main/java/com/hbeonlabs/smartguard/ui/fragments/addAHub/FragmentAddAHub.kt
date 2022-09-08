package com.hbeonlabs.smartguard.ui.fragments.addAHub

import android.view.View
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentAddAHubBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.utils.collectLatestLifeCycleFlow
import com.hbeonlabs.smartguard.utils.makeToast

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
        observe()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_baseline_help)
            setOnClickListener { snackBar("Help") }
        }

        (requireActivity() as MainActivity).binding.toolbarIconEnd2.visibility = View.INVISIBLE

        binding.btnAddHub.setOnClickListener {
            val hubSerialNo = binding.edtAddHubSerial.text.toString()
            val hubSimNo = binding.edtAddHubSimNo.text.toString()
            addAHubViewModel.addHub(hubSerialNo,hubSimNo)
        }

    }

    private fun observe(){
        collectLatestLifeCycleFlow(addAHubViewModel.addHubEvents){
            when(it)
            {
                AddHubEvents.NavigateToPostHubEvent -> {
                    findNavController().navigate(FragmentAddAHubDirections.actionFragmentAddAHubToFragmentPostAddHub())
                }
                is AddHubEvents.PhoneValidationErrorEvent -> {
                    makeToast(it.message)
                }
                is AddHubEvents.SerialNumberValidationErrorEvent -> {
                    makeToast(it.message)
                }
                is AddHubEvents.SQLErrorEvent -> {
                    makeToast(it.message)
                }
            }
        }
    }



}