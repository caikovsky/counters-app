package com.cornershop.counterstest.domain.usecases.get

import com.cornershop.counterstest.data.core.BaseApiResponse
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.data.repository.CounterRepository
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class GetCounterUseCaseImpl @Inject constructor(
    private val counterRepository: CounterRepository
) :
    GetCounterUseCase, BaseApiResponse() {

    override suspend fun invoke(): NetworkResult<List<Counter>> {
        return safeApiCall { counterRepository.getCounters() }
    }

    override suspend fun getLocalCounters(): List<Counter> {
        return counterRepository.getLocalCounters()
    }
}