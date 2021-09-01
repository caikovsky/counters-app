package com.cornershop.counterstest.presentation.ui.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cornershop.counterstest.databinding.FragmentExampleCounterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExampleCounterFragment : Fragment() {

    private var _binding: FragmentExampleCounterBinding? = null
    private val binding: FragmentExampleCounterBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExampleCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

}