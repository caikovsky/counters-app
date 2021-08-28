package com.cornershop.counterstest.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.databinding.FragmentListCountersBinding
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.util.Constants.COUNTER_KEY
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
        observeNavigationBackStack()
        showProgressDialog()
        viewModel.getCounters()
    }

    private fun setListeners() {
        with(binding) {
            listContent!!.createCounterButton.setOnClickListener {
                findNavController().navigate(R.id.action_listCountersFragment_to_createCounterFragment)
            }

            swipeLayout.setOnRefreshListener {
                viewModel.getCounters()
                binding.swipeLayout.isRefreshing = false
            }
        }
    }

    private fun setRecyclerView() {
        binding.listContent?.counterRecycler?.run {
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

    private fun observeNavigationBackStack() {
        findNavController().run {
            val navBackStackEntry = getBackStackEntry(R.id.listCountersFragment)
            val savedStateHandle = navBackStackEntry.savedStateHandle
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME && savedStateHandle.contains(COUNTER_KEY)) {
                    val product = savedStateHandle.get<Counter>(COUNTER_KEY)
                    val oldList = counterAdapter.currentList
                    val newList = oldList.toMutableList().apply {
                        add(product)
                    }
                    counterAdapter.submitList(newList)
                    binding.listContent?.counterRecycler?.smoothScrollToPosition(newList.size - 1)
                    savedStateHandle.remove<Counter>(COUNTER_KEY)
                }
            }

            navBackStackEntry.lifecycle.addObserver(observer)

            viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    navBackStackEntry.lifecycle.removeObserver(observer)
                }
            })
        }
    }

    private fun showProgressDialog() {
        binding.listContent?.container!!.visibility = View.GONE
        binding.progressDialog!!.visibility = View.VISIBLE
    }

    private fun dismissProgressDialog() {
        binding.listContent?.container!!.visibility = View.VISIBLE
        binding.progressDialog!!.visibility = View.GONE
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}