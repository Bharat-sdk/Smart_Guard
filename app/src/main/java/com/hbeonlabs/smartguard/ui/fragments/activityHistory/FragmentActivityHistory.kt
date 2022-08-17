package com.hbeonlabs.smartguard.ui.fragments.activityHistory

import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistoryDate
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistoryItem
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistory
import com.hbeonlabs.smartguard.databinding.FragmentActivityHistoryBinding
import com.hbeonlabs.smartguard.ui.adapters.ActivityHistoryAdapter
import com.hbeonlabs.smartguard.utils.AppConstants
import com.hbeonlabs.smartguard.utils.AppLists

import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class FragmentActivityHistory:BaseFragment<ActivityHistoryViewModel,FragmentActivityHistoryBinding>() {

    private  val activityHistoryViewModel: ActivityHistoryViewModel by inject()
    override fun getViewModel(): ActivityHistoryViewModel {
            return activityHistoryViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_activity_history
    }

    override fun initView() {
        super.initView()

        val formatter = SimpleDateFormat("dd-MMM,yyyy")

        val groupedMapMap: Map<String, List<ActivityHistory>> = AppLists(requireContext()).activityList.groupBy {
            formatter.format(Date(it.activity_history_time_stamp))
        }

        val consolidatedList = mutableListOf<ActivityHistoryItem>()
        for (date:String in groupedMapMap.keys){
            val groupItems: List<ActivityHistory>? = groupedMapMap[date]
            consolidatedList.add(ActivityHistoryDate(date,groupItems?.size.toString()))
            groupItems?.forEach {
                consolidatedList.add(ActivityHistory(it.activity_history_time_stamp,it.activity_history_message,it.hub_id))
            }
        }

        val adapter = ActivityHistoryAdapter(consolidatedList)
        binding.adapter = adapter

    }



}