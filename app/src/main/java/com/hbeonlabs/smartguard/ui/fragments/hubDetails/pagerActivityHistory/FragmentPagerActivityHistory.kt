package com.hbeonlabs.smartguard.ui.fragments.hubDetails.pagerActivityHistory

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistoryDate
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistoryItem
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistoryList
import com.hbeonlabs.smartguard.databinding.FragmentPagerActivityHistoryBinding
import com.hbeonlabs.smartguard.ui.adapters.ActivityHistoryAdapter
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.FragmentHubHomeDirections
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.HubDetailsViewModel
import com.hbeonlabs.smartguard.utils.AppLists
import com.hbeonlabs.smartguard.utils.collectLatestLifeCycleFlow
import kotlinx.coroutines.flow.collectLatest

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FragmentPagerActivityHistory:BaseFragment<HubDetailsViewModel,FragmentPagerActivityHistoryBinding>() {

    private  val hubDetailsViewModel by sharedStateViewModel<HubDetailsViewModel>()
    override fun getViewModel(): HubDetailsViewModel {
            return hubDetailsViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_pager_activity_history
    }

    override fun initView() {
        super.initView()

        observe()

        binding.txtSeeFullActivityHistory.setOnClickListener {
                    findNavController().navigate(FragmentHubHomeDirections.actionFragmentHubDetailsToFragmentActivityHistory(hubDetailsViewModel.hub_id))
        }
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