package com.cornershop.counterstest.presentation.ui.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.databinding.FragmentExampleCounterBinding
import com.cornershop.counterstest.presentation.ui.create.CreateCounterViewModel
import com.cornershop.counterstest.util.DialogButton
import com.cornershop.counterstest.util.DialogUtil
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExampleCounterFragment : Fragment() {

    private var _binding: FragmentExampleCounterBinding? = null
    private val binding: FragmentExampleCounterBinding get() = _binding!!
    private val viewModel: CreateCounterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExampleCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateChips()
        setObservers()
        setBindings()
        setHasOptionsMenu(false)
    }

    private fun setBindings() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun setObservers() {
        viewModel.save.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    findNavController().navigate(R.id.action_exampleCounterFragment_to_listCountersFragment2)
                }

                is NetworkResult.Error -> showErrorDialog()
            }
        }
    }

    private fun showErrorDialog() {
        DialogUtil.getDialog(
            requireActivity(),
            title = resources.getString(R.string.error_creating_counter_title),
            message = resources.getString(R.string.connection_error_description),
            dialogButton = DialogButton(
                text = resources.getString(R.string.ok)
            ) { dialog, _ -> dialog.dismiss() },
        ).show()
    }

    private fun populateChips() {
        renderDrinkExamples()
        renderFoodExamples()
        renderMiscExamples()
    }

    private fun renderMiscExamples() {
        val miscList = resources.getStringArray(R.array.misc_array)

        for (misc in miscList) {
            val chip = Chip(binding.miscExamplesChipGroup.context)
            chip.text = misc
            chip.setOnClickListener { viewModel.saveCounter(misc) }
            binding.miscExamplesChipGroup.addView(chip)
        }
    }

    private fun renderFoodExamples() {
        val foodList = resources.getStringArray(R.array.food_array)

        for (food in foodList) {
            val chip = Chip(binding.foodExamplesChipGroup.context)
            chip.text = food
            chip.setOnClickListener { viewModel.saveCounter(food) }
            binding.foodExamplesChipGroup.addView(chip)
        }
    }

    private fun renderDrinkExamples() {
        val drinkList = resources.getStringArray(R.array.drinks_array)

        for (drink in drinkList) {
            val chip = Chip(binding.drinkExamplesChipGroup.context)
            chip.text = drink
            chip.setOnClickListener { viewModel.saveCounter(drink) }
            binding.drinkExamplesChipGroup.addView(chip)
        }
    }

}