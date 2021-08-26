package com.cornershop.counterstest.domain.usecases.inc

import com.cornershop.counterstest.domain.model.Counter

interface IncrementCounterUseCase {
    suspend operator fun invoke(id: String): Counter
}