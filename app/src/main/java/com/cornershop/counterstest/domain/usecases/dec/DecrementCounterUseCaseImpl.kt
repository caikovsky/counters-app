package com.cornershop.counterstest.domain.usecases.dec

import com.cornershop.counterstest.data.core.BaseApiResponse
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.data.repository.CounterRepository
import com.cornershop.counterstest.data.request.DecrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class DecrementCounterUseCaseImpl @Inject constructor(private val counterRepository: CounterRepository) :
    DecrementCounterUseCase, BaseApiResponse() {

    override suspend fun invoke(id: DecrementCounterRequest): NetworkResult<List<Counter>> {
        return safeApiCall { counterRepository.decrementCounter(id) }
    }
}