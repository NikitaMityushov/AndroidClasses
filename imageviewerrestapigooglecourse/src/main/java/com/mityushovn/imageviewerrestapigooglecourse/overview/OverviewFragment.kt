package com.mityushovn.imageviewerrestapigooglecourse.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.imageviewerrestapigooglecourse.R
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
    ): View {
        adapter = OverviewListAdapter(listOf())

        binding = OverviewFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        recyclerView = binding.overviewRecyclerview.also {
            it.adapter = adapter
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.data.observe(viewLifecycleOwner, { data ->
            updateUI(data)
        })

    }

    private fun updateUI(data: List<MarsProperty>) {
        val adapter = OverviewListAdapter(data)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

}