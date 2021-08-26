package com.cornershop.counterstest.domain.usecases.dec

import com.cornershop.counterstest.domain.model.Counter

interface DecrementCounterUseCase {
    suspend operator fun invoke(id: String): Counter
}