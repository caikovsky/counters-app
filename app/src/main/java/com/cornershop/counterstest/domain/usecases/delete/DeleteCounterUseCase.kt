package com.cornershop.counterstest.domain.usecases.delete

import com.cornershop.counterstest.data.request.DeleteCounterRequest
import com.cornershop.counterstest.domain.model.Counter

interface DeleteCounterUseCase {
    suspend operator fun invoke(id: DeleteCounterRequest): List<Counter>
}