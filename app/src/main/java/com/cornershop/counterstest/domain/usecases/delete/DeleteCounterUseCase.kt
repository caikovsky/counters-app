package com.cornershop.counterstest.domain.usecases.delete

import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.data.request.DeleteCounterRequest
import com.cornershop.counterstest.domain.model.Counter

interface DeleteCounterUseCase {
    suspend operator fun invoke(id: DeleteCounterRequest): NetworkResult<List<Counter>>
}