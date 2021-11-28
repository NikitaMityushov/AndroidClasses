package com.mityushovn.imageviewerrestapigooglecourse.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.imageviewerrestapigooglecourse.databinding.OverviewFragmentBinding

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
    ): View {

        binding = OverviewFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = OverviewListAdapter(listOf(), MarsItemListener {})
        recyclerView = binding.overviewRecyclerview.also {
            it.adapter = adapter
        }

        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        viewModel.data.observe(viewLifecycleOwner, { data ->
//            updateUI(data)
//        })
//
//    }

//    private fun updateUI(data: List<MarsProperty>) {
//        val adapter = OverviewListAdapter(data)
//        recyclerView.adapter = adapter
//    }


}