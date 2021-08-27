package com.cornershop.counterstest.domain.usecases.dec

import com.cornershop.counterstest.data.repository.CounterRepository
import com.cornershop.counterstest.data.request.DecrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class DecrementCounterUseCaseImpl @Inject constructor(private val counterRepository: CounterRepository) :
    DecrementCounterUseCase {

    override suspend fun invoke(id: DecrementCounterRequest): List<Counter> {
        return counterRepository.decrementCounter(id)
    }
}