package com.hbeonlabs.smartguard.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.data.local.models.SecondaryUser
import com.hbeonlabs.smartguard.data.local.models.Sensor
import com.hbeonlabs.smartguard.databinding.ItemSensorBinding

class SensorListAdapter(val context:Context) : RecyclerView.Adapter<SensorListAdapter.SensorViewHolder>() {

    inner class SensorViewHolder(val binding: ItemSensorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Sensor) {
                binding.txtSensorName.text = data.sensor_name
                binding.ibIconSensorMenu.setImageResource(R.drawable.ic_three_dots)
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<Sensor>() {
        override fun areItemsTheSame(
            oldItem: Sensor,
            newItem: Sensor
        ): Boolean {
            return oldItem.sensor_id == newItem.sensor_id
        }

        override fun areContentsTheSame(
            oldItem: Sensor,
            newItem: Sensor
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        return SensorViewHolder(
            ItemSensorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
        holder.binding.ibIconSensorMenu.setOnClickListener {
                // Add Popup Menu
                val popupMenu = PopupMenu(context,holder.binding.ibIconSensorMenu)
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
    }


    private var onEditUserClickListener: ((Sensor,Int) -> Unit)? = null

    fun setEditUserClickListener(listener: (Sensor,Int) -> Unit) {
        onEditUserClickListener = listener
    }

    private var onDeleteUserClickListener: ((Sensor,Int) -> Unit)? = null

    fun setDeleteUserClickListener(listener: (Sensor,Int) -> Unit) {
        onDeleteUserClickListener = listener
    }


}