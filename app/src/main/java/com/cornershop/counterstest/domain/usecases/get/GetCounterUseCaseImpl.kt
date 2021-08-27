package com.cornershop.counterstest.domain.usecases.get

import com.cornershop.counterstest.data.repository.CounterRepository
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class GetCounterUseCaseImpl @Inject constructor(
    private val counterRepository: CounterRepository
) :
    GetCounterUseCase {

    override suspend fun invoke(): List<Counter> {
        return counterRepository.getCounters()
    }
}