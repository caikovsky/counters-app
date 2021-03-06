package com.cornershop.counterstest.domain.usecases.dec

import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.data.request.DecrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter

interface DecrementCounterUseCase {

    suspend operator fun invoke(id: DecrementCounterRequest): NetworkResult<List<Counter>>
}