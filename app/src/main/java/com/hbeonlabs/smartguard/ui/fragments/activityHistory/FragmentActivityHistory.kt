package com.hbeonlabs.smartguard.ui.fragments.activityHistory

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistoryDate
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistoryItem
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistoryList
import com.hbeonlabs.smartguard.databinding.FragmentActivityHistoryBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.ActivityHistoryAdapter
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.FragmentHubHomeDirections
import com.hbeonlabs.smartguard.utils.AppLists
import kotlinx.coroutines.flow.collectLatest

import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class FragmentActivityHistory:BaseFragment<ActivityHistoryViewModel,FragmentActivityHistoryBinding>() {
    val args : FragmentActivityHistoryArgs by navArgs()

    private  val activityHistoryViewModel: ActivityHistoryViewModel by inject()
    override fun getViewModel(): ActivityHistoryViewModel {
            return activityHistoryViewModel
    }

    override fun getLayoutResourceId()=R.layout.fragment_activity_history


    override fun initView() {
        super.initView()

        activityHistoryViewModel.hub_id = args.hubSerialNo
        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.INVISIBLE

        }
        (requireActivity() as MainActivity).binding.toolbarIconEnd2.apply {
            visibility = View.INVISIBLE
        }

        observe()


    }

    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            getViewModel().getActivityHistory().collectLatest {
                val activityList: ArrayList<ActivityHistoryList> = arrayListOf()
                it.forEach {
                    activityList.add(
                        ActivityHistoryList(
                            it.activity_history_time_stamp,
                            it.activity_history_message,
                            it.hub_serial_number
                        )
                    )
                }

                val formatter = SimpleDateFormat("dd-MMM,yyyy")

                val groupedMapMap: Map<String, List<ActivityHistoryList>> = activityList.groupBy {
                    formatter.format(Date(it.activity_history_time_stamp))
                }

                val consolidatedList = mutableListOf<ActivityHistoryItem>()
                for (date: String in groupedMapMap.keys) {
                    val groupItems: List<ActivityHistoryList>? = groupedMapMap[date]
                    consolidatedList.add(ActivityHistoryDate(date, groupItems?.size.toString()))
                    groupItems?.forEach {
                        consolidatedList.add(
                            ActivityHistoryList(
                                it.activity_history_time_stamp,
                                it.activity_history_message,
                                it.hub_id
                            )
                        )
                    }
                }

                val adapter = ActivityHistoryAdapter(consolidatedList)
                binding.adapter = adapter
            }
        }
    }



}