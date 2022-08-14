package com.hbeonlabs.smartguard.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistory
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistoryDate
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistoryItem
import com.hbeonlabs.smartguard.databinding.ItemActivityHistoryBinding
import com.hbeonlabs.smartguard.databinding.ItemActivityHistoryDateBinding
import java.text.SimpleDateFormat
import java.util.*

class ActivityHistoryAdapter(
    private val items: List<ActivityHistoryItem>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ActivityHistoryItem.TYPE_DATE ->
                DateViewHolder(ItemActivityHistoryDateBinding.inflate(layoutInflater,parent,false))
            else ->
                GeneralViewHolder(ItemActivityHistoryBinding.inflate(layoutInflater,parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ActivityHistoryItem.TYPE_DATE -> (holder as DateViewHolder).bind(
                item = items[position] as ActivityHistoryDate,
            )
            ActivityHistoryItem.TYPE_ACTIVITY_HISTORY -> (holder as GeneralViewHolder).bind(
                item = items[position] as ActivityHistory
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class DateViewHolder(val binding: ItemActivityHistoryDateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ActivityHistoryDate) {
            val cal = Calendar.getInstance().timeInMillis
            val formatter = SimpleDateFormat("dd-MMM,yyyy")
            if (formatter.format(cal) == item.activity_history_time_stamp)
            {
                binding.txtDate.text = "Today"
            }
            else{
                binding.txtDate.text = item.activity_history_time_stamp
            }
            binding.txtListSize.text = item.size + " events"
        }
    }

    inner class GeneralViewHolder(val binding: ItemActivityHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ActivityHistory) {
            binding.txtActivity.text = item.activity_history_message
            val formatter = SimpleDateFormat(" HH:mm")

                binding.txtTime.text = formatter.format(item.activity_history_time_stamp)
        }
    }

}