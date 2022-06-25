package com.cornershop.counterstest.domain.usecases

import com.cornershop.counterstest.data.model.request.DeleteCounterRequest
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.util.toDomain
import javax.inject.Inject

class DeleteCounterUseCase @Inject constructor(private val counterRepositoryImpl: CounterRepositoryImpl) {
    suspend operator fun invoke(id: DeleteCounterRequest): List<Counter> =
        counterRepositoryImpl.deleteCounter(id).toDomain()
}
