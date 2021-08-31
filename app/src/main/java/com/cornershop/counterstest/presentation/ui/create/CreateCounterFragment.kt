package com.cornershop.counterstest.presentation.ui.create

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.databinding.FragmentCreateCounterBinding
import com.cornershop.counterstest.util.logD
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCounterFragment : Fragment() {
    private var _binding: FragmentCreateCounterBinding? = null
    private val binding: FragmentCreateCounterBinding get() = _binding!!
    private val viewModel: CreateCounterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        observeStates()
        setBindings()
    }

    private fun setBindings() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            createViewModel = viewModel
        }
    }

    private fun observeStates() {
        viewModel.save.observe(viewLifecycleOwner) { counters ->
            when (counters) {
                is NetworkResult.Success -> {
                    viewModel.toggleProgressDialog(true)
                    findNavController().popBackStack()
                }
                is NetworkResult.Error -> {
                    viewModel.toggleProgressDialog(false)
                    logD("Error!")
                }
                is NetworkResult.Loading -> viewModel.toggleProgressDialog(true)
            }
        }
    }

    private fun setListeners() {
        binding.saveButton?.setOnClickListener {
            viewModel.saveCounter(binding.contentLayout?.textField?.editText?.text.toString())
        }
    }


    // FIXME
    private fun showKeyboard(activity: Activity, view: View, textInputLayout: TextInputLayout) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        textInputLayout.requestFocus()
        inputMethodManager.showSoftInput(view, 0)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}