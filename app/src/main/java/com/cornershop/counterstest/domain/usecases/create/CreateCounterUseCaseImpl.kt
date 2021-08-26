package com.cornershop.counterstest.domain.usecases.create

import com.cornershop.counterstest.data.repository.CounterRepository
import com.cornershop.counterstest.domain.model.Counter

class CreateCounterUseCaseImpl(private val counterRepository: CounterRepository) :
    CreateCounterUseCase {
    override suspend fun invoke(title: String): Counter {
        return counterRepository.createCounter(title)
    }
}