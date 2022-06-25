package com.cornershop.counterstest.data.repository

import com.cornershop.counterstest.data.CounterService
import com.cornershop.counterstest.data.model.GetCounterResponse
import com.cornershop.counterstest.data.request.CreateCounterRequest
import com.cornershop.counterstest.data.request.DecrementCounterRequest
import com.cornershop.counterstest.data.request.DeleteCounterRequest
import com.cornershop.counterstest.data.request.IncrementCounterRequest
import com.cornershop.counterstest.domain.CounterRepository
import com.cornershop.counterstest.domain.model.Counter
import retrofit2.Response
import javax.inject.Inject

class CounterRepositoryImpl @Inject constructor(
    private val counterService: CounterService
) : CounterRepository {

    override suspend fun getCounters(): List<GetCounterResponse> = counterService.getCounters()

    override suspend fun createCounter(request: CreateCounterRequest): Response<List<Counter>> =
        counterService.createCounter(request)

    override suspend fun deleteCounter(request: DeleteCounterRequest): Response<List<Counter>> =
        counterService.deleteCounter(request)

    override suspend fun incrementCounter(request: IncrementCounterRequest): Response<List<Counter>> =
        counterService.incrementCounter(request)

    override suspend fun decrementCounter(request: DecrementCounterRequest): Response<List<Counter>> =
        counterService.decrementCounter(request)
}
