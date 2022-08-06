package com.hbeonlabs.smartguard.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hbeonlabs.smartguard.data.local.models.OnBoardingData
import com.hbeonlabs.smartguard.databinding.ItemViewPagerBinding

class ViewPagerAdapter(
   val list:ArrayList<OnBoardingData>,

):RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {
    private lateinit var binding:ItemViewPagerBinding

    inner class ViewPagerViewHolder(binding: ItemViewPagerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: OnBoardingData) {
            binding.onBoardingData = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        binding = ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
       val data = list[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}


