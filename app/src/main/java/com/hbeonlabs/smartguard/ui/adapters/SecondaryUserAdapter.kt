package com.hbeonlabs.smartguard.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.SecondaryUser
import com.hbeonlabs.smartguard.databinding.ItemHubLayoutBinding
import com.hbeonlabs.smartguard.databinding.ItemSecandoryNumberBinding

class SecondaryUserAdapter : RecyclerView.Adapter<SecondaryUserAdapter.SecondaryUserViewHolder>() {

    inner class SecondaryUserViewHolder(val binding: ItemSecandoryNumberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SecondaryUser) {
            if (data.user_name.isNotEmpty())
            {
                binding.data = (data)
            }
            else{
                binding.txtName.text = "EMPTY SLOT"
                binding.txtNumber.text = "Tap to register a new user"
                binding.ibIconEnd.setImageResource(R.drawable.ic_three_dots)
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<SecondaryUser>() {
        override fun areItemsTheSame(
            oldItem: SecondaryUser,
            newItem: SecondaryUser
        ): Boolean {
            return oldItem.user_id == newItem.user_id
        }

        override fun areContentsTheSame(
            oldItem: SecondaryUser,
            newItem: SecondaryUser
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondaryUserViewHolder {
        return SecondaryUserViewHolder(
            ItemSecandoryNumberBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SecondaryUserViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
        if (data.user_name.isNotEmpty())
        {
            holder.bind(data)
        }

        holder.binding.ibIconEnd.setOnClickListener {
            onClickListener?.let { it(data,position) }
        }

    }

    private var onClickListener: ((SecondaryUser,Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (SecondaryUser,Int) -> Unit) {
        onClickListener = listener
    }


}