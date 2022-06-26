package com.cornershop.counterstest.presentation.ui.examples

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.counterstest.data.model.request.CreateCounterRequest
import com.cornershop.counterstest.domain.usecases.CreateCounterUseCase
import com.cornershop.counterstest.presentation.model.Counter
import com.cornershop.counterstest.presentation.util.toPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ExampleCounterViewModel @Inject constructor(
    private val createCounterUseCase: CreateCounterUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<ExampleState<List<Counter>>> =
        MutableStateFlow(ExampleState.Uninitialized)
    val state: StateFlow<ExampleState<List<Counter>>> = _state.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        ExampleState.Uninitialized
    )

    fun onEvent(event: ExampleCounterEvent) {
        when (event) {
            is ExampleCounterEvent.OnChipClick -> createExampleCounter(event.example)
        }
    }

    private fun createExampleCounter(counterName: String) {
        viewModelScope.launch {
            _state.value = ExampleState.Loading

            delay(2000L) // placeholder delay

            runCatching {
                createCounterUseCase(title = CreateCounterRequest(title = counterName))
            }
                .onSuccess {
                    _state.value = ExampleState.Success(it.toPresentationModel())
                }
                .onFailure { throwable ->
                    Log.e(this::class.simpleName, "createExampleCounter: ${throwable.message}")
                    _state.value = ExampleState.Error
                }
        }
    }

    sealed class ExampleCounterEvent {
        data class OnChipClick(val example: String) : ExampleCounterEvent()
    }

    sealed class ExampleState<out T : Any> {
        object Uninitialized : ExampleState<Nothing>()
        object Loading : ExampleState<Nothing>()
        object Error : ExampleState<Nothing>()
        data class Success<out T : Any>(val data: T) : ExampleState<T>()
    }
}
