package com.mityushovn.imageviewerrestapigooglecourse.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mityushovn.imageviewerrestapigooglecourse.R
import com.mityushovn.imageviewerrestapigooglecourse.databinding.DetailsFragmentBinding
import com.mityushovn.imageviewerrestapigooglecourse.models.MarsProperty
import com.mityushovn.imageviewerrestapigooglecourse.overview.OverviewViewModel

class DetailsFragment : Fragment() {
    private lateinit var binding: DetailsFragmentBinding
    private lateinit var property: MarsProperty
    // or private val property: DetailsFragmentArgs by navArgs(), the same thing

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this)[DetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailsFragmentBinding.inflate(layoutInflater, container, false)
        property = DetailsFragmentArgs.fromBundle(requireArguments()).marsProperty
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}