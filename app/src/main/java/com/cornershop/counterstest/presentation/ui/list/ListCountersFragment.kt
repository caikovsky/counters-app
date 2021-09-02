package com.cornershop.counterstest.presentation.ui.list

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
        configureViewModel()
        updateItemCount(counterAdapter.itemCount)
    }

    private fun configureViewModel() {
        with(viewModel) {
            toggleProgressDialog(true)
            getCounters()
        }
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

            lifecycleOwner = viewLifecycleOwner
            listViewModel = viewModel

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

                    // TODO: Improve logic
                    if (counterAdapter.itemCount == 0) {
                        viewModel.renderNoResultsLayout(true)
                    } else {
                        viewModel.renderNoResultsLayout(false)
                    }

                    updateCountTimes()
                    updateItemCount(counterAdapter.itemCount)
                    return false
                }
            })
        }
    }

    private fun updateItemCount(itemCount: Int) {
        binding.listContent.listBodyContent.recyclerViewComponent.itemCountTotal.text =
            String.format(
                resources.getString(R.string.n_items),
                itemCount
            )
    }

    private fun updateCountTimes() {
        binding.listContent.listBodyContent.recyclerViewComponent.itemTimesTotal.text =
            String.format(
                resources.getString(R.string.n_times),
                viewModel.calculateCountTimes(counterAdapter)
            )
    }

    private fun setRecyclerView() {
        binding.listContent.listBodyContent.recyclerViewComponent.counterRecycler.run {
            setHasFixedSize(true)
            adapter = counterAdapter
        }
    }

    private fun renderCounterList(counterList: List<Counter>) {
        counterAdapter.addData(counterList)
        counterAdapter.notifyDataSetChanged()
        updateCountTimes()
        updateItemCount(counterList.size)
        viewModel.renderNoResultsLayout(false)
        viewModel.renderEmptyLayout(false)
    }

    private fun observeStates() {
        viewModel.counters.observe(viewLifecycleOwner) { counters ->
            when (counters) {
                is NetworkResult.Success -> {
                    viewModel.toggleProgressDialog(false)
                    viewModel.renderErrorLayout(false)

                    if (counters.data.isNullOrEmpty()) {
                        viewModel.renderEmptyLayout(true)
                    } else {
                        renderCounterList(counters.data)
                        binding.swipeLayout.isRefreshing = false
                    }
                }
                is NetworkResult.Error -> {
                    viewModel.toggleProgressDialog(false)
                    viewModel.renderErrorLayout(true)
                }
            }
        }

        viewModel.dialogError.observe(viewLifecycleOwner) { counter ->
            val title = String.format(resources.getString(R.string.error_updating_counter_title), counter.title, counter.count)
            DialogUtil.getDialog(
                requireActivity(),
                title = title,
                message = resources.getString(R.string.connection_error_description),
                dialogButton = DialogButton(
                    text = resources.getString(R.string.retry)
                ) { _, _ -> viewModel.retryButton() },
                negativeButton = DialogButton(
                    resources.getString(R.string.dismiss)
                ) { dialogInterface, _ -> dialogInterface.dismiss() }
            ).show()
        }

        viewModel.incCounter.observe(viewLifecycleOwner) { response ->
            if (response is NetworkResult.Success) {
                renderCounterList(response.data!!)
            }
        }

        viewModel.decCounter.observe(viewLifecycleOwner) { response ->
            if (response is NetworkResult.Success) {
                renderCounterList(response.data!!)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoadingLayoutBeingShown ->
            binding.swipeLayout.isEnabled = !isLoadingLayoutBeingShown
            binding.listContent.searchCounter.isEnabled = !isLoadingLayoutBeingShown
            // TODO: Disable Search View
        }

        viewModel.isError.observe(viewLifecycleOwner) { isErrorLayoutBeingShown ->
            binding.swipeLayout.isEnabled = !isErrorLayoutBeingShown
            // TODO: Disable Search View
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
                    binding.listContent.listBodyContent.recyclerViewComponent.counterRecycler.smoothScrollToPosition(
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}