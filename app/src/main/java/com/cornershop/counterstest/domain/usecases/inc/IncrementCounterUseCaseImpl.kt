package com.cornershop.counterstest.domain.usecases.inc

import com.cornershop.counterstest.data.repository.CounterRepository
import com.cornershop.counterstest.domain.model.Counter

class IncrementCounterUseCaseImpl(private val counterRepository: CounterRepository) :
    IncrementCounterUseCase {
    override suspend fun invoke(id: String): Counter {
        return counterRepository.incrementCounter(id)
    }
}