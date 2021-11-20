package com.mityushovn.imageviewerrestapigooglecourse.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.imageviewerrestapigooglecourse.R
import com.mityushovn.imageviewerrestapigooglecourse.databinding.MarsItemBinding
import com.mityushovn.imageviewerrestapigooglecourse.models.MarsProperty

class OverviewListAdapter(private val list: List<MarsProperty>): RecyclerView.Adapter<OverviewListAdapter.MarsItemVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsItemVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.mars_item, parent,false)
        return MarsItemVH(MarsItemBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: MarsItemVH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
    // View Holder
    class MarsItemVH(private val binding: MarsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(property: MarsProperty) {
            // TODO: 19.11.2021  
            // debug
            binding.marsProperty = property
            binding.executePendingBindings()
        }
    }
}


