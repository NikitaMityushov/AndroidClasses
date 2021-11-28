package com.mityushovn.imageviewerrestapigooglecourse.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.imageviewerrestapigooglecourse.R
import com.mityushovn.imageviewerrestapigooglecourse.databinding.MarsItemBinding
import com.mityushovn.imageviewerrestapigooglecourse.models.MarsProperty
import timber.log.Timber

class OverviewListAdapter(private val list: List<MarsProperty>, val clickListener: MarsItemListener): RecyclerView.Adapter<OverviewListAdapter.MarsItemVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsItemVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.mars_item, parent,false)
        return MarsItemVH(MarsItemBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: MarsItemVH, position: Int) {
        holder.bind(list[position], clickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    // View Holder
    class MarsItemVH(private val binding: MarsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var marsProperty: MarsProperty

        fun bind(property: MarsProperty, clickListener: MarsItemListener) {
            this.marsProperty = property
            binding.marsProperty = property
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}

class MarsItemListener(val clickListener: (marsProperty: MarsProperty) -> Unit) {
    fun onClick(marsProperty: MarsProperty) {
        Timber.d("onClick is pushed")
        clickListener(marsProperty)
    }
}
