package com.cornershop.counterstest.domain.usecases.inc

import com.cornershop.counterstest.data.model.IncrementCounterResponse
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.data.request.IncrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class IncrementCounterUseCase @Inject constructor(private val counterRepositoryImpl: CounterRepositoryImpl) {
    suspend operator fun invoke(id: IncrementCounterRequest): List<Counter> =
        counterRepositoryImpl.incrementCounter(id).toDomain()
}

private fun List<IncrementCounterResponse>.toDomain(): List<Counter> = map { item ->
    Counter(
        id = item.id,
        title = item.title,
        count = item.count
    )
}
