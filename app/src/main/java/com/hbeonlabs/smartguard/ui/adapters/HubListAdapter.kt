package com.hbeonlabs.smartguard.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.databinding.ItemHubLayoutBinding

class HubListAdapter : RecyclerView.Adapter<HubListAdapter.HubViewHolder>() {

    

    inner class HubViewHolder(val binding: ItemHubLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Hub) {
            binding.hubData = data
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Hub>() {
        override fun areItemsTheSame(
            oldItem: Hub,
            newItem: Hub
        ): Boolean {
            return oldItem.hub_serial_number == newItem.hub_serial_number
        }

        override fun areContentsTheSame(
            oldItem: Hub,
            newItem: Hub
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HubViewHolder {
        return HubViewHolder(
            ItemHubLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: HubViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            onClickListener?.let { it(data) }
        }

    }

    private var onClickListener: ((Hub) -> Unit)? = null

    fun setOnItemClickListener(listener: (Hub) -> Unit) {
        onClickListener = listener
    }


}