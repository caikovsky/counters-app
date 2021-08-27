package com.cornershop.counterstest.domain.usecases.inc

import com.cornershop.counterstest.data.repository.CounterRepository
import com.cornershop.counterstest.data.request.IncrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class IncrementCounterUseCaseImpl @Inject constructor(private val counterRepository: CounterRepository) :
    IncrementCounterUseCase {
    override suspend fun invoke(id: IncrementCounterRequest): List<Counter> {
        return counterRepository.incrementCounter(id)
    }
}