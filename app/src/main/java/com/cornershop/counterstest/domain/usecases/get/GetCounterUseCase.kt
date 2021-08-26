package com.cornershop.counterstest.domain.usecases.get

import com.cornershop.counterstest.domain.model.Counter

interface GetCounterUseCase {

    suspend operator fun invoke(): List<Counter>
}