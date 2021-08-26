package com.cornershop.counterstest.domain.usecases.delete

import com.cornershop.counterstest.data.repository.CounterRepository
import com.cornershop.counterstest.domain.model.Counter

class DeleteCounterUseCaseImpl(private val counterRepository: CounterRepository) :
    DeleteCounterUseCase {
    override suspend fun invoke(id: String): Counter {
        return counterRepository.deleteCounter(id)
    }
}