package com.cornershop.counterstest.domain.usecases.inc

import com.cornershop.counterstest.data.core.BaseApiResponse
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.data.repository.CounterRepository
import com.cornershop.counterstest.data.request.IncrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class IncrementCounterUseCaseImpl @Inject constructor(private val counterRepository: CounterRepository) :
    IncrementCounterUseCase, BaseApiResponse() {
    override suspend fun invoke(id: IncrementCounterRequest): NetworkResult<List<Counter>> {
        return safeApiCall { counterRepository.incrementCounter(id) }
    }
}