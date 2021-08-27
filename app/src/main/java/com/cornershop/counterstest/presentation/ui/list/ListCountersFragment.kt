package com.cornershop.counterstest.presentation.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cornershop.counterstest.databinding.FragmentListCountersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListCountersFragment : Fragment() {

    private var _binding: FragmentListCountersBinding? = null
    private val binding: FragmentListCountersBinding get() = _binding!!
    private val viewModel: ListCounterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCountersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStates()
    }

    private fun observeStates() {
        viewModel.counters.observe(viewLifecycleOwner) {
            Log.d("TAG", it.size.toString())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}