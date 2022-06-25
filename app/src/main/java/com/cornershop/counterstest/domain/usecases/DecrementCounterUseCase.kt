package com.cornershop.counterstest.domain.usecases

import com.cornershop.counterstest.data.core.BaseApiResponse
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.data.request.DecrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class DecrementCounterUseCase @Inject constructor(private val counterRepositoryImpl: CounterRepositoryImpl) :
    BaseApiResponse() {

    suspend operator fun invoke(id: DecrementCounterRequest): NetworkResult<List<Counter>> {
        return safeApiCall { counterRepositoryImpl.decrementCounter(id) }
    }
}
