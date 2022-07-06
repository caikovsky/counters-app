package com.cornershop.counterstest.domain.usecases

import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.util.toDomain
import javax.inject.Inject

class CreateCounterUseCase @Inject constructor(private val counterRepositoryImpl: CounterRepositoryImpl) {
    suspend operator fun invoke(title: String): List<Counter> =
        counterRepositoryImpl.createCounter(title).toDomain()
}
