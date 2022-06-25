package com.cornershop.counterstest.domain.usecases.create

import com.cornershop.counterstest.data.core.BaseApiResponse
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.data.request.CreateCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class CreateCounterUseCaseImpl @Inject constructor(private val counterRepositoryImpl: CounterRepositoryImpl) :
    CreateCounterUseCase, BaseApiResponse() {
    override suspend fun invoke(title: CreateCounterRequest): NetworkResult<List<Counter>> {
        return safeApiCall { counterRepositoryImpl.createCounter(title) }
    }
}