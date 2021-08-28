package com.cornershop.counterstest.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.databinding.FragmentListCountersBinding
import com.cornershop.counterstest.util.logD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListCountersFragment : Fragment() {

    private var _binding: FragmentListCountersBinding? = null
    private val binding: FragmentListCountersBinding get() = _binding!!
    private val viewModel: ListCounterViewModel by viewModels()

    private val counterAdapter = ListCounterAdapter()

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
        setRecyclerView()
        setListeners()
        observeStates()
        showProgressDialog()
        viewModel.getCounters()
    }

    private fun setListeners() {
        with(binding) {
            swipeLayout.setOnRefreshListener {
                viewModel.getCounters()
                binding.swipeLayout.isRefreshing = false
            }
        }
    }

    private fun setRecyclerView() {
        binding.counterRecycler?.run {
            setHasFixedSize(true)
            adapter = counterAdapter
        }
    }

    private fun observeStates() {
        viewModel.counters.observe(viewLifecycleOwner) { counters ->
            when (counters) {
                is NetworkResult.Success -> {
                    dismissProgressDialog()
                    binding.swipeLayout.isRefreshing = false
                    counterAdapter.submitList(counters.data)
                }
                is NetworkResult.Error -> {
                    dismissProgressDialog()
                    logD("Error!")
                }
            }
        }
    }

    private fun showProgressDialog() {
        binding.counterRecycler!!.visibility = View.GONE
        binding.progressDialog!!.visibility = View.VISIBLE
    }

    private fun dismissProgressDialog() {
        binding.counterRecycler!!.visibility = View.VISIBLE
        binding.progressDialog!!.visibility = View.GONE
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}