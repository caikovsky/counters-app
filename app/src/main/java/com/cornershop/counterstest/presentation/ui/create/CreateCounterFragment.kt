package com.cornershop.counterstest.presentation.ui.create

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.Annotation
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.databinding.FragmentCreateCounterBinding
import com.cornershop.counterstest.util.DialogButton
import com.cornershop.counterstest.util.DialogUtil
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
        configureDisclaimerText()
    }

    private fun configureDisclaimerText() {
        val fullText = getText(R.string.create_counter_disclaimer) as SpannedString
        val spannableString = SpannableString(fullText)
        binding.contentLayout.textDisclaimer.text = spannableString
        val annotations = fullText.getSpans(0, fullText.length, Annotation::class.java)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                findNavController().navigate(R.id.action_createCounterFragment_to_exampleCounterFragment)
            }
        }

        annotations?.find {
            it.value == "examples_link"
        }?.let {
            spannableString.apply {
                // Make it clickable
                setSpan(
                    clickableSpan,
                    fullText.getSpanStart(it),
                    fullText.getSpanEnd(it),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                // Make it gray
                setSpan(
                    ForegroundColorSpan(Color.GRAY),
                    fullText.getSpanStart(it),
                    fullText.getSpanEnd(it),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                // Make it underlined
                setSpan(
                    UnderlineSpan(),
                    fullText.getSpanStart(it),
                    fullText.getSpanEnd(it),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        binding.contentLayout.textDisclaimer.apply {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
        }
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
                    showErrorDialog()
                }
                is NetworkResult.Loading -> viewModel.toggleProgressDialog(true)
            }
        }
    }

    private fun setListeners() {
        binding.saveButton.setOnClickListener {
            viewModel.saveCounter(binding.contentLayout.textField.editText?.text.toString())
        }
    }

    // FIXME
    private fun showKeyboard(activity: Activity, view: View, textInputLayout: TextInputLayout) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        textInputLayout.requestFocus()
        inputMethodManager.showSoftInput(view, 0)
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}