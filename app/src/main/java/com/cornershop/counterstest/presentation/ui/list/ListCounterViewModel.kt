package com.cornershop.counterstest.presentation.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.counterstest.domain.usecases.DecrementCounterUseCase
import com.cornershop.counterstest.domain.usecases.DeleteCounterUseCase
import com.cornershop.counterstest.domain.usecases.GetCounterUseCase
import com.cornershop.counterstest.domain.usecases.IncrementCounterUseCase
import com.cornershop.counterstest.presentation.model.Counter
import com.cornershop.counterstest.presentation.ui.list.ListCounterViewModel.ListCounterEvent.*
import com.cornershop.counterstest.presentation.util.toPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
internal class ListCounterViewModel @Inject constructor(
    private val getCounterUseCase: GetCounterUseCase,
    private val incrementCounterUseCase: IncrementCounterUseCase,
    private val decrementCounterUseCase: DecrementCounterUseCase,
    private val deleteCounterUseCase: DeleteCounterUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<State<ListCounterState>> = MutableStateFlow(State.Loading)
    val state: StateFlow<State<ListCounterState>> by lazy {
        _state
            .onStart { fetchCounters() }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                State.Loading
            )
    }

    private fun fetchCounters() {
        viewModelScope.launch {
            _state.value = State.Loading

            runCatching {
                getCounterUseCase()
            }.onSuccess { response ->
                val totalAmount = response.size
                val totalValue = response.sumOf { it.count }

                _state.value = State.Success(
                    ListCounterState(
                        counters = response.toPresentationModel(),
                        totalAmount = totalAmount,
                        totalValue = totalValue
                    )
                )
            }.onFailure { throwable ->
                Log.e(this::class.simpleName, "getCounterUseCase: ${throwable.message}")
                _state.value = State.Error
            }
        }
    }

    fun onEvent(event: ListCounterEvent) {
        when (event) {
            is OnIncrementClick -> incrementCounter(event.counter)
            is OnDecrementClick -> decrementCounter(event.counter)
            is OnRetryClick -> fetchCounters()
        }
    }

    data class ListCounterState(
        val counters: List<Counter>,
        val totalAmount: Int,
        val totalValue: Int
    )

    sealed class ListCounterEvent {
        data class OnIncrementClick(val counter: Counter) : ListCounterEvent()
        data class OnDecrementClick(val counter: Counter) : ListCounterEvent()
        object OnRetryClick : ListCounterEvent()
    }

    private fun incrementCounter(counter: Counter) {
        viewModelScope.launch {
            runCatching {
                incrementCounterUseCase(counter.id)
            }.onSuccess { response ->
                val totalAmount = response.size
                val totalValue = response.sumOf { it.count }

                _state.value = State.Success(
                    ListCounterState(
                        counters = response.toPresentationModel(),
                        totalAmount = totalAmount,
                        totalValue = totalValue
                    )
                )
            }.onFailure { throwable ->
                Log.e(this::class.simpleName, "incrementCounterUseCase: ${throwable.message}")
                _state.value = State.Error
            }
        }
    }

    private fun decrementCounter(counter: Counter) {
        viewModelScope.launch {
            runCatching {
                decrementCounterUseCase(counter.id)
            }.onSuccess { response ->
                val totalAmount = response.size
                val totalValue = response.sumOf { it.count }

                _state.value = State.Success(
                    ListCounterState(
                        counters = response.toPresentationModel(),
                        totalAmount = totalAmount,
                        totalValue = totalValue
                    )
                )
            }.onFailure { throwable ->
                Log.e(this::class.simpleName, "decrementCounterUseCase: ${throwable.message}")
                _state.value = State.Error
            }
        }
    }

    val _showToolbar = MutableLiveData<Boolean>()
    val showToolbar: LiveData<Boolean> get() = _showToolbar

    private val _dialogError = MutableLiveData<CounterError>()
    val dialogError: LiveData<CounterError> get() = _dialogError

    fun toggleSearchToolbar(show: Boolean) {
        _showToolbar.value = show
    }

    fun deleteCounter(counters: List<Counter>) {
        viewModelScope.launch {
            val content = arrayListOf<Counter>()

            withContext(Dispatchers.IO) {
                val runningTasks = counters.map { counter ->
                    async {
                        val apiResponse = deleteCounterUseCase(counter.id)
                        counter.id to apiResponse
                    }
                }

                val responses = runningTasks.awaitAll()

                // TODO: Handle errors
                content.addAll(responses.last().second.toPresentationModel())
            }

//            _deleteCounter.value = content
        }
    }
}

internal sealed class State<out T : Any> {
    object Loading : State<Nothing>()
    object Error : State<Nothing>()
    data class Success<out T : Any>(val data: T) : State<T>()
}

internal class CounterError(val counter: Counter, val type: String = "update")
