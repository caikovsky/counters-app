package com.cornershop.counterstest.presentation.ui.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.FragmentExampleCounterBinding
import com.google.android.material.chip.Chip
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateChips()
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
            binding.miscExamplesChipGroup.addView(chip)
        }
    }

    private fun renderFoodExamples() {
        val foodList = resources.getStringArray(R.array.food_array)

        for (food in foodList) {
            val chip = Chip(binding.foodExamplesChipGroup.context)
            chip.text = food
            binding.foodExamplesChipGroup.addView(chip)
        }
    }

    private fun renderDrinkExamples() {
        val drinkList = resources.getStringArray(R.array.drinks_array)

        for (drink in drinkList) {
            val chip = Chip(binding.drinkExamplesChipGroup.context)
            chip.text = drink
            binding.drinkExamplesChipGroup.addView(chip)
        }
    }

}