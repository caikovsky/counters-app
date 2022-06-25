package com.cornershop.counterstest.domain.usecases.get

import com.cornershop.counterstest.data.model.GetCounterResponse
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class GetCounterUseCase @Inject constructor(
    private val counterRepositoryImpl: CounterRepositoryImpl
) {
    suspend operator fun invoke(): List<Counter> = counterRepositoryImpl.getCounters().toDomain()
}

private fun List<GetCounterResponse>.toDomain(): List<Counter> =
    map { item ->
        Counter(
            id = item.id,
            title = item.title,
            count = item.count
        )
    }
