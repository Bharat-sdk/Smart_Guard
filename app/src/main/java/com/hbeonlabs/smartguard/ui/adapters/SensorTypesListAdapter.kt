package com.hbeonlabs.smartguard.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.data.local.models.Sensor
import com.hbeonlabs.smartguard.data.local.models.SensorTypes
import com.hbeonlabs.smartguard.databinding.ItemSensorBinding
import com.hbeonlabs.smartguard.databinding.ItemTypeOfSensorsBinding

class SensorTypesListAdapter(val context:Context) : RecyclerView.Adapter<SensorTypesListAdapter.SensorTypeViewHolder>() {

    inner class SensorTypeViewHolder(val binding: ItemTypeOfSensorsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SensorTypes) {
            binding.ivSensorImage.setImageResource(data.sensor_image)
            binding.tvSensorName.text = data.sensor_name


        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<SensorTypes>() {
        override fun areItemsTheSame(
            oldItem: SensorTypes,
            newItem: SensorTypes
        ): Boolean {
            return oldItem.sensor_model_number == newItem.sensor_model_number
        }

        override fun areContentsTheSame(
            oldItem: SensorTypes,
            newItem: SensorTypes
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorTypeViewHolder {
        return SensorTypeViewHolder(
            ItemTypeOfSensorsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SensorTypeViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)


        holder.binding.ivAddSensor.setOnClickListener {
            onClickListener?.let { it(data,position) }
        }

    }


    private var onClickListener: ((SensorTypes,Int) -> Unit)? = null

    fun setClickListener(listener: (SensorTypes,Int) -> Unit) {
        onClickListener = listener
    }



}