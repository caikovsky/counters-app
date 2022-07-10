package com.cornershop.counterstest.presentation.ui.create

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.counterstest.domain.usecases.CreateCounterUseCase
import com.cornershop.counterstest.domain.usecases.TextInputUseCase
import com.cornershop.counterstest.domain.usecases.TextInputUseCase.TextInputState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CreateCounterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    textInputUseCase: TextInputUseCase = TextInputUseCase(),
    private val createCounterUseCase: CreateCounterUseCase
) : ViewModel() {
    private val counterName: StateFlow<String> = savedStateHandle.getStateFlow(COUNTER_NAME_KEY, "")

    private val _isLoading = MutableStateFlow(value = false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _action = MutableStateFlow<CreateCounterAction?>(value = null)
    private val action: StateFlow<CreateCounterAction?> get() = _action

    val state: StateFlow<CreateCounterState> by lazy {
        combine(
            isLoading,
            action,
            textInputUseCase(counterName)
        ) { isLoading, action, input ->
            CreateCounterState(
                isLoading = isLoading,
                action = action,
                textInput = input
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), CreateCounterState())
    }

    private fun saveCounter(title: String) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1_500L) // placeholder

            runCatching {
                createCounterUseCase(title)
            }.onSuccess { _ ->
                _isLoading.value = false
                _action.value = CreateCounterAction.CounterCreated
            }.onFailure { throwable ->
                _isLoading.value = false
                _action.value = CreateCounterAction.ShowErrorDialog
                Log.e(this::class.simpleName, "createCounterUseCase: ${throwable.message}")
            }
        }
    }

    fun onEvent(event: CreateCounterEvent) {
        when (event) {
            is CreateCounterEvent.OnCounterNameChange -> savedStateHandle[COUNTER_NAME_KEY] =
                event.name
            is CreateCounterEvent.OnCreateCounterClick -> {
                if (event.name.isNotEmpty()) saveCounter(event.name)
            }
        }
    }

    sealed class CreateCounterAction {
        object CounterCreated : CreateCounterAction()
        object ShowErrorDialog : CreateCounterAction()
    }

    sealed class CreateCounterEvent {
        data class OnCreateCounterClick(val name: String) : CreateCounterEvent()
        data class OnCounterNameChange(val name: String) : CreateCounterEvent()
    }

    data class CreateCounterState(
        val isLoading: Boolean = false,
        val action: CreateCounterAction? = null,
        val textInput: TextInputState? = null
    )

    private companion object {
        private const val COUNTER_NAME_KEY = "counter_name"
    }
}
