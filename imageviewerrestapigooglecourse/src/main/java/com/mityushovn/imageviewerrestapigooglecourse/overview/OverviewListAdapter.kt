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
    class MarsItemVH(private val binding: MarsItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var marsProperty: MarsProperty

        fun bind(property: MarsProperty) {
            // TODO: 19.11.2021  
            // debug
            this.marsProperty = property
            binding.marsProperty = property
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            // val directions = OverviewFragmentDirections.actionOverviewFragmentToDetailsFragment()
            v?.findNavController()?.navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailsFragment(marsProperty))
            Timber.d("click!!!")
        }
    }
}


