package com.mityushov.investor.screens.aboutFragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.mityushov.investor.R
import com.mityushov.investor.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }
}