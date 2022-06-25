package com.cornershop.counterstest.presentation.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.data.request.DecrementCounterRequest
import com.cornershop.counterstest.data.request.DeleteCounterRequest
import com.cornershop.counterstest.data.request.IncrementCounterRequest
import com.cornershop.counterstest.domain.usecases.DecrementCounterUseCase
import com.cornershop.counterstest.domain.usecases.DeleteCounterUseCase
import com.cornershop.counterstest.domain.usecases.GetCounterUseCase
import com.cornershop.counterstest.domain.usecases.IncrementCounterUseCase
import com.cornershop.counterstest.presentation.model.Counter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
internal class ListCounterViewModel @Inject constructor(
    private val getCounterUseCase: GetCounterUseCase,
    private val incrementCounterUseCase: IncrementCounterUseCase,
    private val decrementCounterUseCase: DecrementCounterUseCase,
    private val deleteCounterUseCase: DeleteCounterUseCase
) :
    ViewModel() {

    private val _isCreateButton = MutableLiveData<Boolean>()
    val isCreateButton: LiveData<Boolean> get() = _isCreateButton

    val _showToolbar = MutableLiveData<Boolean>()
    val showToolbar: LiveData<Boolean> get() = _showToolbar

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isListEmpty = MutableLiveData<Boolean>()
    val isListEmpty: LiveData<Boolean> get() = _isListEmpty

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    private val _noResults = MutableLiveData<Boolean>()
    val noResults: LiveData<Boolean> get() = _noResults

    private val _dialogError = MutableLiveData<CounterError>()
    val dialogError: LiveData<CounterError> get() = _dialogError

    private val _counters = MutableLiveData<State<List<Counter>>>()
    val counters: LiveData<State<List<Counter>>> get() = _counters

    private val _incCounter = MutableLiveData<State<List<Counter>>>()
    val incCounter: LiveData<State<List<Counter>>> get() = _incCounter

    private val _decCounter = MutableLiveData<State<List<Counter>>>()
    val decCounter: LiveData<State<List<Counter>>> get() = _decCounter

    private val _deleteCounter = MutableLiveData<List<Counter>>()
    val deleteCounter: LiveData<List<Counter>> get() = _deleteCounter

    fun getCounters() {
        viewModelScope.launch {
            _counters.value = State.Loading

            runCatching {
                getCounterUseCase()
            }.onSuccess { response ->
                val result = response.map { domainModel ->
                    Counter(
                        id = domainModel.id,
                        title = domainModel.title,
                        count = domainModel.count,
                        selected = false
                    )
                }

                _counters.value = State.Success(result)
            }.onFailure { throwable ->
                Log.e(this::class.simpleName, "getCounterUseCase: ${throwable.message}")
                _counters.value = State.Error
            }
        }
    }

    fun incrementCounter(counter: Counter) {
        viewModelScope.launch {
            _incCounter.value = State.Loading

            runCatching {
                incrementCounterUseCase(IncrementCounterRequest(counter.id))
            }.onSuccess { list ->
                val result = list.map { domainModel ->
                    Counter(
                        id = domainModel.id,
                        title = domainModel.title,
                        count = domainModel.count,
                        selected = false
                    )
                }

                _incCounter.value = State.Success(result)
            }.onFailure { throwable ->
                Log.e(this::class.simpleName, "incrementCounterUseCase: ${throwable.message}")
                counter.count = counter.count.inc()
                _dialogError.value = CounterError(counter)
            }
        }
    }

    fun decrementCounter(counter: Counter) {
        _decCounter.value = State.Loading

        viewModelScope.launch {
            decrementCounterUseCase(DecrementCounterRequest(counter.id)).let {
                when (it) {
                    is NetworkResult.Error -> {
                        counter.count = counter.count.dec()
                        _dialogError.value = CounterError(counter)
                    }
                    is NetworkResult.Success -> {
//                        _decCounter.value = it.data
                    }
                    is NetworkResult.Loading -> {}
                }
            }
        }
    }

    fun toggleProgressDialog(show: Boolean) {
        _isLoading.value = show
    }

    fun renderErrorLayout(showLayout: Boolean) {
        _isError.value = showLayout
    }

    fun renderNoResultsLayout(show: Boolean) {
        _noResults.value = show
    }

    fun renderEmptyLayout(show: Boolean) {
        _isListEmpty.value = show
    }

    fun toggleSearchToolbar(show: Boolean) {
        _showToolbar.value = show
    }

    fun retryButton() {
        toggleProgressDialog(true)
        renderErrorLayout(false)
        getCounters()
    }

    fun calculateCountTimes(list: List<Counter>): Int {
        var total = 0

        for (counter in list) {
            total += counter.count
        }

        return total
    }

    fun deleteCounter(counters: List<Counter>) {
        viewModelScope.launch {
            val content = arrayListOf<Counter>()

            withContext(Dispatchers.IO) {
                val runningTasks = counters.map { counter ->
                    async {
                        val apiResponse = deleteCounterUseCase(DeleteCounterRequest(counter.id))
                        counter.id to apiResponse
                    }
                }

                val responses = runningTasks.awaitAll()
                // TODO: Handle errors
//                content.addAll(responses.last().second.data!!)
            }

            _deleteCounter.value = content
        }
    }

    fun renderCreateButton(show: Boolean) {
        _isCreateButton.value = show
    }
}

internal sealed class State<out T : Any> {
    object Loading : State<Nothing>()
    object Error : State<Nothing>()
    data class Success<out T : Any>(val data: T) : State<T>()
}

internal class CounterError(val counter: Counter, val type: String = "update")
