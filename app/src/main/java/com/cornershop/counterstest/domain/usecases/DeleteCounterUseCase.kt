package com.cornershop.counterstest.domain.usecases

import com.cornershop.counterstest.data.model.DeleteCounterResponse
import com.cornershop.counterstest.data.model.request.DeleteCounterRequest
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class DeleteCounterUseCase @Inject constructor(private val counterRepositoryImpl: CounterRepositoryImpl) {
    suspend operator fun invoke(id: DeleteCounterRequest): List<Counter> =
        counterRepositoryImpl.deleteCounter(id).toDomain()

    private fun List<DeleteCounterResponse>.toDomain(): List<Counter> = map { item ->
        Counter(
            id = item.id,
            title = item.title,
            count = item.count
        )
    }
}
