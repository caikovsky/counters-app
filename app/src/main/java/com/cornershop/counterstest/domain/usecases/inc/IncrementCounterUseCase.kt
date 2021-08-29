package com.cornershop.counterstest.domain.usecases.inc

import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.data.request.IncrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter

interface IncrementCounterUseCase {
    suspend operator fun invoke(id: IncrementCounterRequest): NetworkResult<List<Counter>>
}