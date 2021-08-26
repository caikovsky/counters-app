package com.cornershop.counterstest.domain.usecases.dec

import com.cornershop.counterstest.data.repository.CounterRepository
import com.cornershop.counterstest.domain.model.Counter

class DecrementCounterUseCaseImpl(private val counterRepository: CounterRepository) :
    DecrementCounterUseCase {
    override suspend fun invoke(id: String): Counter {
        return counterRepository.decrementCounter(id)
    }
}