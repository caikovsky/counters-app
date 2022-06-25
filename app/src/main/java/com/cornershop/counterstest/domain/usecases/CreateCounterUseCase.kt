package com.cornershop.counterstest.domain.usecases

import com.cornershop.counterstest.data.model.CreateCounterResponse
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.data.model.request.CreateCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class CreateCounterUseCase @Inject constructor(private val counterRepositoryImpl: CounterRepositoryImpl) {
    suspend operator fun invoke(title: CreateCounterRequest): List<Counter> {
        return counterRepositoryImpl.createCounter(title).toDomain()
    }

    private fun List<CreateCounterResponse>.toDomain(): List<Counter> =
        map { item ->
            Counter(
                id = item.id,
                title = item.title,
                count = item.count
            )
        }
}
