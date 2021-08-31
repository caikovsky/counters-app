package com.cornershop.counterstest.presentation.ui.list

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
import com.cornershop.counterstest.util.DialogButton
import com.cornershop.counterstest.util.DialogUtil
import com.cornershop.counterstest.util.logD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListCountersFragment : Fragment() {

    private var _binding: FragmentListCountersBinding? = null
    private val binding: FragmentListCountersBinding get() = _binding!!
    private val viewModel: ListCounterViewModel by viewModels()

    private val counterAdapter: ListCounterAdapter = ListCounterAdapter(
        { counter -> decrementOnClick(counter) },
        { counter -> incrementOnClick(counter) })

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
        configureSwipeLayout()
        observeStates()
        observeNavigationBackStack()
        viewModel.getCounters()
        updateItemCount(counterAdapter.itemCount)
        binding.listViewModel = viewModel
    }

    private fun decrementOnClick(counter: Counter) {
        viewModel.decrementCounter(counter)
    }

    private fun incrementOnClick(counter: Counter) {
        viewModel.incrementCounter(counter)
    }

    private fun configureSwipeLayout() {
        binding.swipeLayout.setColorSchemeResources(R.color.orange)
    }

    private fun setListeners() {
        with(binding) {
            listContent.createCounterButton.setOnClickListener {
                findNavController().navigate(R.id.action_listCountersFragment_to_createCounterFragment)
            }

            swipeLayout.setOnRefreshListener {
                viewModel.getCounters()
                binding.swipeLayout.isRefreshing = false
            }

            listContent.searchCounter.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    counterAdapter.filter.filter(newText)
                    updateCountTimes()
                    updateItemCount(counterAdapter.itemCount)
                    return false
                }
            })
        }
    }

    private fun updateItemCount(itemCount: Int) {
        binding.listContent.listBodyContent.itemCountTotal.text =
            String.format(
                resources.getString(R.string.n_items),
                itemCount
            )
    }

    private fun calculateCountTimes(): Int {
        var total = 0

        for (counter in counterAdapter.counterListFiltered) {
            total += counter.count
        }

        return total
    }

    private fun updateCountTimes() {
        binding.listContent.listBodyContent.itemTimesTotal.text =
            String.format(resources.getString(R.string.n_times), calculateCountTimes())
    }

    private fun setRecyclerView() {
        binding.listContent.listBodyContent.counterRecycler.run {
            setHasFixedSize(true)
            adapter = counterAdapter
        }
    }

    private fun renderCounterList(counterList: List<Counter>) {
        counterAdapter.addData(counterList)
        counterAdapter.notifyDataSetChanged()
        updateCountTimes()
        updateItemCount(counterList.size)
    }

    private fun observeStates() {
        viewModel.counters.observe(viewLifecycleOwner) { counters ->
            when (counters) {
                is NetworkResult.Success -> {
                    dismissProgressDialog()
                    renderErrorLayout(false)
                    if (counters.data.isNullOrEmpty()) {
                        viewModel._isListEmpty.value = true
                    } else {
                        renderCounterList(counters.data)
                        binding.swipeLayout.isRefreshing = false
                    }
                }
                is NetworkResult.Error -> {
                    dismissProgressDialog()
                    renderErrorLayout(true)
                }
            }
        }

        viewModel.incCounter.observe(viewLifecycleOwner) { counters ->
            when (counters) {
                is NetworkResult.Success -> {
                    renderCounterList(counters.data!!)
                }
                is NetworkResult.Error -> {
                    //TODO: add dialog
                    logD("incCounter Error!")
                }
            }
        }

        viewModel.decCounter.observe(viewLifecycleOwner) { counters ->
            when (counters) {
                is NetworkResult.Success -> {
                    renderCounterList(counters.data!!)
                }
                is NetworkResult.Error -> {
                    logD("decCounter Error!")
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
                    binding.listContent.listBodyContent.counterRecycler.smoothScrollToPosition(
                        newList.size - 1
                    )
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
        viewModel._isLoading.value = true
    }

    private fun dismissProgressDialog() {
        viewModel._isLoading.value = false
    }

    private fun renderErrorLayout(showLayout: Boolean) {
        viewModel._isError.value = showLayout
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}