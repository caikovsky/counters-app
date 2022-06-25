package com.cornershop.counterstest.domain.usecases

import com.cornershop.counterstest.data.model.request.IncrementCounterRequest
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.util.toDomain
import javax.inject.Inject

class IncrementCounterUseCase @Inject constructor(private val counterRepositoryImpl: CounterRepositoryImpl) {
    suspend operator fun invoke(id: IncrementCounterRequest): List<Counter> =
        counterRepositoryImpl.incrementCounter(id).toDomain()
}
