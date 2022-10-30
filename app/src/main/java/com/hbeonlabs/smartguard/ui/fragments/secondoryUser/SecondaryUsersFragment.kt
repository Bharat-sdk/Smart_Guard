package com.hbeonlabs.smartguard.ui.fragments.secondoryUser

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentSecondaryUsersBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.SecondaryUserAdapter
import com.hbeonlabs.smartguard.utils.AppConstants
import com.hbeonlabs.smartguard.utils.AppLists
import com.hbeonlabs.smartguard.utils.collectLatestLifeCycleFlow
import com.hbeonlabs.smartguard.utils.makeToast
import kotlinx.coroutines.flow.collectLatest

import org.koin.android.ext.android.inject


class SecondaryUsersFragment:BaseFragment<SecondaryUserViewModel,FragmentSecondaryUsersBinding>() {
    private val args:SecondaryUsersFragmentArgs by navArgs()
    private  val secondaryUserViewModel: SecondaryUserViewModel by inject()
    private lateinit var adapter:SecondaryUserAdapter
    override fun getViewModel(): SecondaryUserViewModel {
            return secondaryUserViewModel
    }

    override fun getLayoutResourceId()= R.layout.fragment_secondary_users


    override fun initView() {
        super.initView()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.visibility = View.INVISIBLE
        (requireActivity() as MainActivity).binding.toolbarIconEnd2.visibility = View.INVISIBLE

        secondaryUserViewModel.hubId = args.hubId

        adapter = SecondaryUserAdapter(requireContext())
        adapter.setAddUserClickListener { _, i ->
            // add user to the hub and slot given
            findNavController().navigate(SecondaryUsersFragmentDirections.actionSecondaryUsersFragmentToAddSecondaryUserFragment(i,secondaryUserViewModel.hubId))
        }
        adapter.setEditUserClickListener { secondaryUser, _ ->

            findNavController().navigate(SecondaryUsersFragmentDirections.actionSecondaryUsersFragmentToEditSecondaryUserFragment(secondaryUser))

        }
        adapter.setDeleteUserClickListener { secondaryUser, _ ->
            secondaryUserViewModel.deleteSecondaryUser(secondaryUser)
        }

        
        adapter.differ.submitList(AppLists(requireContext()).secondaryUserList)
        binding.adapter = adapter

        observe()
    }

    fun observe()
    {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            secondaryUserViewModel.getSecondaryUsersUsingHub(args.hubId).collectLatest { databaseList ->

                val newList = AppLists(requireContext()).secondaryUserList

                databaseList.forEach {
                    newList[it.slot] = it
                }

                adapter.differ.submitList(null)
                adapter.differ.submitList(newList)

            }
        }

        collectLatestLifeCycleFlow(secondaryUserViewModel.secondaryUserEvents)
        {
            when(it)
            {
                SecondaryUserEvents.DeleteSecondaryUserSuccessEvent -> {
                    makeToast(AppConstants.DELETE_SECONDARY_USER_SUCCESSFULLY)
                }
                is SecondaryUserEvents.SQLErrorEvent -> {
                    makeToast(it.message)
                }
            }
        }
    }




}