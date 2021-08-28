package com.cornershop.counterstest.presentation.ui.create

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cornershop.counterstest.databinding.FragmentCreateCounterBinding
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