package com.cornershop.counterstest.presentation.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.usecases.get.GetCounterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCounterViewModel @Inject constructor(
    private val getCounterUseCase: GetCounterUseCase,
) :
    ViewModel() {

    private val _counters = MutableLiveData<NetworkResult<List<Counter>>>()
    val counters: LiveData<NetworkResult<List<Counter>>> get() = _counters

    init {
        getCounters()
    }

    private fun getCounters() {
        _counters.value = NetworkResult.Loading()

        viewModelScope.launch {
            getCounterUseCase().let {
                _counters.value = it
            }
        }
    }
}