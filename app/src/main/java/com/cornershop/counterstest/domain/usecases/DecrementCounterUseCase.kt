package com.cornershop.counterstest.domain.usecases

import com.cornershop.counterstest.data.model.DecrementCounterResponse
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.data.model.request.DecrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class DecrementCounterUseCase @Inject constructor(private val counterRepositoryImpl: CounterRepositoryImpl) {

    suspend operator fun invoke(id: DecrementCounterRequest): List<Counter> =
        counterRepositoryImpl.decrementCounter(id).toDomain()

    private fun List<DecrementCounterResponse>.toDomain(): List<Counter> =
        map { item ->
            Counter(
                id = item.id,
                title = item.title,
                count = item.count
            )
        }
}
