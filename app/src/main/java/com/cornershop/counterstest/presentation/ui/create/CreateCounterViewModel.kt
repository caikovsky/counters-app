package com.cornershop.counterstest.presentation.ui.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.counterstest.domain.usecases.CreateCounterUseCase
import com.cornershop.counterstest.presentation.model.Counter
import com.cornershop.counterstest.presentation.ui.list.State
import com.cornershop.counterstest.presentation.util.toPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CreateCounterViewModel @Inject constructor(
    private val createCounterUseCase: CreateCounterUseCase
) : ViewModel() {

    private val _save = MutableLiveData<State<List<Counter>>>()
    val save: LiveData<State<List<Counter>>> get() = _save

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun saveCounter(title: String) {
        viewModelScope.launch {
            _save.value = State.Loading

            runCatching {
                createCounterUseCase(title)
            }.onSuccess { response ->
                _save.value = State.Success(response.toPresentationModel())
            }.onFailure { throwable ->
                Log.e(this::class.simpleName, "createCounterUseCase: ${throwable.message}")
                _save.value = State.Error
            }
        }
    }

    fun toggleProgressDialog(show: Boolean) {
        _isLoading.value = show
    }
}
