package com.cornershop.counterstest.presentation.ui.create

import androidx.lifecycle.ViewModel
import com.cornershop.counterstest.domain.usecases.create.CreateCounterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateCounterViewModel @Inject constructor(createCounterUseCase: CreateCounterUseCase) :
    ViewModel() {
}