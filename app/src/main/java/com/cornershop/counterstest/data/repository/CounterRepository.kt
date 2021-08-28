package com.cornershop.counterstest.data.repository

import com.cornershop.counterstest.data.CounterService
import com.cornershop.counterstest.data.request.CreateCounterRequest
import com.cornershop.counterstest.data.request.DecrementCounterRequest
import com.cornershop.counterstest.data.request.DeleteCounterRequest
import com.cornershop.counterstest.data.request.IncrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import retrofit2.Response
import javax.inject.Inject

class CounterRepository @Inject constructor(
    private val counterService: CounterService
) {

    suspend fun getCounters(): Response<List<Counter>> = counterService.getCounters()

    suspend fun createCounter(request: CreateCounterRequest): Response<List<Counter>> =
        counterService.createCounter(request)

    suspend fun deleteCounter(request: DeleteCounterRequest): List<Counter> =
        counterService.deleteCounter(request)

    suspend fun incrementCounter(request: IncrementCounterRequest): List<Counter> =
        counterService.incrementCounter(request)

    suspend fun decrementCounter(request: DecrementCounterRequest): List<Counter> =
        counterService.decrementCounter(request)
}