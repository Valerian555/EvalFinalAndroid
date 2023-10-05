package com.technipixl.evalfinaleandroid.ui.trending

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.technipixl.evalfinaleandroid.databinding.FragmentTrendingBinding

class TrendingFragment : Fragment() {
    private lateinit var binding: FragmentTrendingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrendingBinding.inflate(layoutInflater)

        return binding.root
    }
}