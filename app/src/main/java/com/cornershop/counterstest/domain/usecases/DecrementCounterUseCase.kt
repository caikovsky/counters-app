package com.cornershop.counterstest.domain.usecases

import com.cornershop.counterstest.data.model.request.DecrementCounterRequest
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.util.toDomain
import javax.inject.Inject

class DecrementCounterUseCase @Inject constructor(private val counterRepositoryImpl: CounterRepositoryImpl) {

    suspend operator fun invoke(id: DecrementCounterRequest): List<Counter> =
        counterRepositoryImpl.decrementCounter(id).toDomain()
}
