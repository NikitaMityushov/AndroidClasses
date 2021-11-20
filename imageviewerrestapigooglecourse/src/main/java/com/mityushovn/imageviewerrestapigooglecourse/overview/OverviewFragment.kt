package com.mityushovn.imageviewerrestapigooglecourse.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.imageviewerrestapigooglecourse.R
import com.mityushovn.imageviewerrestapigooglecourse.databinding.ActivityMainBinding
import com.mityushovn.imageviewerrestapigooglecourse.databinding.OverviewFragmentBinding
import com.mityushovn.imageviewerrestapigooglecourse.models.MarsProperty

class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this)[OverviewViewModel::class.java]
    }

    private lateinit var binding: OverviewFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OverviewListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = OverviewListAdapter(listOf())

        binding = OverviewFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.activity

        recyclerView = binding.overviewRecyclerview.also {
            it.adapter = adapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.data.observe(viewLifecycleOwner, {data ->
            updateUI(data)
        })

    }

    private fun updateUI(data: List<MarsProperty>) {
        val adapter = OverviewListAdapter(data)
        recyclerView.adapter = adapter
    }

}