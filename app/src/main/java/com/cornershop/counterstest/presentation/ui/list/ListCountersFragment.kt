package com.cornershop.counterstest.presentation.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.util.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.FragmentListCountersBinding
import com.cornershop.counterstest.presentation.model.Counter
import com.cornershop.counterstest.util.Constants.COUNTER_KEY
import com.cornershop.counterstest.util.DialogButton
import com.cornershop.counterstest.util.DialogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListCountersFragment : Fragment() {

    private var _binding: FragmentListCountersBinding? = null
    private val binding: FragmentListCountersBinding get() = _binding!!
    private val viewModel: ListCounterViewModel by viewModels()
    private var actionMode: ActionMode? = null
    private val counterAdapter: ListCounterAdapter = ListCounterAdapter(
        { counter -> decrementOnClick(counter) },
        { counter -> incrementOnClick(counter) },
        { position -> selectCounterOnLongPress(position) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCountersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setUpBindings()
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
            renderCreateButton(true)
            toggleSearchToolbar(true)
            getCounters()
        }
    }

    private fun shareCounter() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val content = formatShareList().joinToString("\n")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, content)
        startActivity(Intent.createChooser(sharingIntent, resources.getString(R.string.share)))
    }

    private fun formatShareList(): List<String> {
        val itemList = arrayListOf<String>()

        for (item in counterAdapter.counterListFiltered) {
            if (item.selected) itemList.add(
                String.format(
                    resources.getString(R.string.n_per_counter_name),
                    item.count,
                    item.title
                )
            )
        }

        return itemList
    }

    private fun getSelectedItems(): List<Counter> {
        val items = mutableListOf<Counter>()

        for (item in counterAdapter.counterListFiltered) {
            if (item.selected) {
                items.add(item)
            }
        }

        return items
    }

    private fun deleteItem() {
        val countersToDelete = getSelectedItems()
        val title: String = if (countersToDelete.isNotEmpty() && countersToDelete.size == 1) {
            String.format(
                resources.getString(R.string.delete_x_question),
                countersToDelete[0].title
            )
        } else {
            String.format(resources.getString(R.string.delete_n_items), countersToDelete.size)
        }

        DialogUtil.getDialog(
            requireActivity(),
            title = title,
            dialogButton = DialogButton(
                text = resources.getString(R.string.delete)
            ) { _, _ ->
                actionMode?.finish()
                viewModel.deleteCounter(countersToDelete)
            },
            negativeButton = DialogButton(
                resources.getString(R.string.cancel)
            ) { dialogInterface, _ -> dialogInterface.dismiss() }
        ).show()
    }

    private fun decrementOnClick(counter: Counter) {
        if (actionMode == null) viewModel.decrementCounter(counter)
    }

    private fun incrementOnClick(counter: Counter) {
        if (actionMode == null) viewModel.incrementCounter(counter)
    }

    private fun selectCounterOnLongPress(position: Int): Boolean {
        enableActionMode(position)
        return true
    }

    private fun enableActionMode(position: Int) {
        if (actionMode == null) {
            actionMode = activity?.startActionMode(object : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    mode?.menuInflater?.inflate(R.menu.menu_delete, menu)
                    viewModel.renderCreateButton(false)
                    viewModel.toggleSearchToolbar(false)
                    return true
                }

                override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                    return false
                }

                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    return when (item?.itemId) {
                        R.id.action_delete -> {
                            deleteItem()
                            true
                        }
                        R.id.menu_share -> {
                            shareCounter()
                            mode?.finish()
                            true
                        }
                        else -> false
                    }
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                    viewModel.renderCreateButton(true)
                    viewModel.toggleSearchToolbar(true)
                    counterAdapter.selectedItems.clear()
                    val counters = counterAdapter.counterListFiltered

                    for (counter in counters) {
                        if (counter.selected) {
                            counter.selected = false
                        }
                    }

                    counterAdapter.notifyDataSetChanged()
                    actionMode = null
                }
            })
        }

        counterAdapter.toggleSelection(position)
        val size = counterAdapter.selectedItems.size

        if (size == 0) {
            actionMode?.finish()
            viewModel.renderCreateButton(true)
            viewModel.toggleSearchToolbar(true)
        } else {
            actionMode?.title = String.format(resources.getString(R.string.n_selected), size)
            actionMode?.invalidate()
        }
    }

    private fun configureSwipeLayout() {
        binding.swipeLayout.setColorSchemeResources(R.color.orange)
    }

    private fun setUpBindings() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            listViewModel = viewModel
        }
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

            searchCounter.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        counterAdapter.filter.filter(newText)

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
                viewModel.calculateCountTimes(counterAdapter.counterListFiltered)
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
        viewModel.counters.observe(viewLifecycleOwner) { response ->
            when (response) {
                is State.Loading -> viewModel.toggleProgressDialog(true)
                is State.Success -> {
                    viewModel.toggleProgressDialog(false)
                    viewModel.renderErrorLayout(false)

                    if (response.data.isEmpty()) {
                        viewModel.renderEmptyLayout(true)
                    } else {
                        renderCounterList(response.data)
                        binding.swipeLayout.isRefreshing = false
                    }
                }
                is State.Error -> {
                    viewModel.toggleProgressDialog(false)
                    viewModel.renderErrorLayout(true)
                }
            }
        }

        viewModel.deleteCounter.observe(viewLifecycleOwner) { counters ->
            if (counters.isNullOrEmpty()) {
                viewModel.toggleProgressDialog(false)
                viewModel.renderErrorLayout(false)
                viewModel.renderEmptyLayout(true)
            } else {
                renderCounterList(counters)
                binding.swipeLayout.isRefreshing = false
            }
        }

        viewModel.dialogError.observe(viewLifecycleOwner) { response ->
            when (response.type) {
                "update" -> {
                    val title =
                        String.format(
                            resources.getString(R.string.error_updating_counter_title),
                            response.counter.title,
                            response.counter.count
                        )

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

                "delete" -> {
                    DialogUtil.getDialog(
                        requireActivity(),
                        title = resources.getString(R.string.error_deleting_counter_title),
                        message = resources.getString(R.string.connection_error_description),
                        dialogButton = DialogButton(
                            text = resources.getString(R.string.dismiss)
                        ) { dialog, _ -> dialog.dismiss() }
                    ).show()
                }

                else -> {
                    DialogUtil.getDialog(
                        requireActivity(),
                        title = resources.getString(R.string.generic_error_title),
                        message = resources.getString(R.string.generic_error_description),
                        dialogButton = DialogButton(
                            text = resources.getString(R.string.retry)
                        ) { dialog, _ -> dialog.dismiss() },
                    ).show()
                }
            }
        }

        viewModel.incCounter.observe(viewLifecycleOwner) { response ->
            if (response is State.Success) {
                renderCounterList(response.data)
            }
        }

        viewModel.decCounter.observe(viewLifecycleOwner) { response ->
            if (response is State.Success) {
                renderCounterList(response.data)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoadingLayoutBeingShown ->
            binding.swipeLayout.isEnabled = !isLoadingLayoutBeingShown
            binding.searchCounter.isEnabled = !isLoadingLayoutBeingShown
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

            viewLifecycleOwner.lifecycle.addObserver(
                LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        navBackStackEntry.lifecycle.removeObserver(observer)
                    }
                }
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
