package com.cornershop.counterstest.presentation.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.data.request.DecrementCounterRequest
import com.cornershop.counterstest.data.request.IncrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.usecases.dec.DecrementCounterUseCase
import com.cornershop.counterstest.domain.usecases.get.GetCounterUseCase
import com.cornershop.counterstest.domain.usecases.inc.IncrementCounterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCounterViewModel @Inject constructor(
    private val getCounterUseCase: GetCounterUseCase,
    private val incrementCounterUseCase: IncrementCounterUseCase,
    private val decrementCounterUseCase: DecrementCounterUseCase,
) :
    ViewModel() {

    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    val _isListEmpty = MutableLiveData<Boolean>()
    val isListEmpty: LiveData<Boolean> get() = _isListEmpty

    val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    private val _counters = MutableLiveData<NetworkResult<List<Counter>>>()
    val counters: LiveData<NetworkResult<List<Counter>>> get() = _counters

    private val _incCounter = MutableLiveData<NetworkResult<List<Counter>>>()
    val incCounter: LiveData<NetworkResult<List<Counter>>> get() = _incCounter

    private val _decCounter = MutableLiveData<NetworkResult<List<Counter>>>()
    val decCounter: LiveData<NetworkResult<List<Counter>>> get() = _decCounter

    fun getCounters() {
        _counters.value = NetworkResult.Loading()

        viewModelScope.launch {
            getCounterUseCase().let {
                _counters.value = it
            }
        }
    }

    fun incrementCounter(counter: Counter) {
        _incCounter.value = NetworkResult.Loading()

        viewModelScope.launch {
            incrementCounterUseCase(IncrementCounterRequest(counter.id)).let {
                _incCounter.value = it
            }
        }
    }

    fun decrementCounter(counter: Counter) {
        _decCounter.value = NetworkResult.Loading()

        viewModelScope.launch {
            decrementCounterUseCase(DecrementCounterRequest(counter.id)).let {
                _decCounter.value = it
            }
        }
    }

    fun toggleProgressDialog(show: Boolean) {
        _isLoading.value = show
    }

    fun renderErrorLayout(showLayout: Boolean) {
        _isError.value = showLayout
    }

    fun retryButton() {
        toggleProgressDialog(true)
        renderErrorLayout(false)
        getCounters()
    }

    fun calculateCountTimes(adapter: ListCounterAdapter): Int {
        var total = 0

        for (counter in adapter.counterListFiltered) {
            total += counter.count
        }

        return total
    }
}