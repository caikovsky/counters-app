package com.cornershop.counterstest.domain.usecases.get

import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.domain.model.Counter
import retrofit2.Response

interface GetCounterUseCase {

    suspend operator fun invoke(): NetworkResult<List<Counter>>
}