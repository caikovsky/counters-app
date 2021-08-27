package com.cornershop.counterstest.domain.usecases.create

import com.cornershop.counterstest.data.repository.CounterRepository
import com.cornershop.counterstest.data.request.CreateCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class CreateCounterUseCaseImpl @Inject constructor(private val counterRepository: CounterRepository) :
    CreateCounterUseCase {
    override suspend fun invoke(title: CreateCounterRequest): List<Counter> {
        return counterRepository.createCounter(title)
    }
}