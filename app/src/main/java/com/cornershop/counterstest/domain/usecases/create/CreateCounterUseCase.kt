package com.cornershop.counterstest.domain.usecases.create

import com.cornershop.counterstest.data.request.CreateCounterRequest
import com.cornershop.counterstest.domain.model.Counter

interface CreateCounterUseCase {

    suspend operator fun invoke(title: CreateCounterRequest): List<Counter>
}