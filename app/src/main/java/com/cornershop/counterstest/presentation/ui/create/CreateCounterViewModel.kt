package com.cornershop.counterstest.presentation.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.data.request.CreateCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.usecases.create.CreateCounterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCounterViewModel @Inject constructor(private val createCounterUseCase: CreateCounterUseCase) :
    ViewModel() {

    private val _save = MutableLiveData<NetworkResult<List<Counter>>>()
    val save: LiveData<NetworkResult<List<Counter>>> get() = _save

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun saveCounter(title: String) {
        _save.value = NetworkResult.Loading()

        viewModelScope.launch {
            createCounterUseCase(CreateCounterRequest(title)).let {
                _save.value = it
            }
        }
    }

    fun saveCounterLocally(counters: List<Counter>) {
        viewModelScope.launch {
            createCounterUseCase.createLocalCounters(counters)
        }
    }

    fun toggleProgressDialog(show: Boolean) {
        _isLoading.value = show
    }

}