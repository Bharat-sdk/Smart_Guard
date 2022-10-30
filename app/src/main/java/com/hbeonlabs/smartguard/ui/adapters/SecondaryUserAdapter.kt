package com.hbeonlabs.smartguard.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.SecondaryUser
import com.hbeonlabs.smartguard.databinding.ItemHubLayoutBinding
import com.hbeonlabs.smartguard.databinding.ItemSecandoryNumberBinding

class SecondaryUserAdapter(val context:Context) : RecyclerView.Adapter<SecondaryUserAdapter.SecondaryUserViewHolder>() {

    inner class SecondaryUserViewHolder(val binding: ItemSecandoryNumberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SecondaryUser) {
            if (data.user_name.isNotEmpty() || data.user_name.isNotBlank())
            {
                binding.txtName.text = data.user_name
                binding.txtNumber.text = data.user_phone_number
                binding.ivPic.setImageURI(data.user_pic.toUri())
                binding.ibIconEnd.setImageResource(R.drawable.ic_three_dots)
            }
            else{
                binding.txtName.text = "EMPTY SLOT"
                binding.txtNumber.text = "Tap to register a new user"
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
            if (data.user_name.isNotBlank() || data.user_phone_number.isNotBlank())
            {
                // Add Popup Menu
                val popupMenu = PopupMenu(context,holder.binding.ibIconEnd)
                popupMenu.setForceShowIcon(true)
                popupMenu.menuInflater.inflate(R.menu.popup_menu_secandory_user,popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.popup_menu_edit ->{
                            // Edit Secondary User
                            onEditUserClickListener?.let { it(data,position) }

                        }
                        R.id.popup_menu_delete -> {
                            // Delete Secondary User
                            onDeleteUserClickListener?.let { it(data,position) }
                        }
                    }
                    true
                })
                popupMenu.show()
            }
            else{
                // Navigate to Add Secondary Fragment
                onAddUserClickListener?.let { it(data,position) }
            }
        }

    }

    private var onAddUserClickListener: ((SecondaryUser,Int) -> Unit)? = null

    fun setAddUserClickListener(listener: (SecondaryUser,Int) -> Unit) {
        onAddUserClickListener = listener
    }


    private var onEditUserClickListener: ((SecondaryUser,Int) -> Unit)? = null

    fun setEditUserClickListener(listener: (SecondaryUser,Int) -> Unit) {
        onEditUserClickListener = listener
    }

    private var onDeleteUserClickListener: ((SecondaryUser,Int) -> Unit)? = null

    fun setDeleteUserClickListener(listener: (SecondaryUser,Int) -> Unit) {
        onDeleteUserClickListener = listener
    }


}