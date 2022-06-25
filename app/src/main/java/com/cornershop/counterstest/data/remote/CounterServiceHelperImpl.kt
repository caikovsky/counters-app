package com.cornershop.counterstest.data.remote

import com.cornershop.counterstest.data.CounterService
import com.cornershop.counterstest.data.model.GetCounterResponse
import com.cornershop.counterstest.data.request.CreateCounterRequest
import com.cornershop.counterstest.data.request.DecrementCounterRequest
import com.cornershop.counterstest.data.request.DeleteCounterRequest
import com.cornershop.counterstest.data.request.IncrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import retrofit2.Response
import javax.inject.Inject

class CounterServiceHelperImpl @Inject constructor(private val counterService: CounterService) :
    CounterServiceHelper {

    override suspend fun getCounters(): List<GetCounterResponse> = counterService.getCounters()

    override suspend fun createCounter(title: CreateCounterRequest): Response<List<Counter>> =
        counterService.createCounter(title)

    override suspend fun deleteCounter(id: DeleteCounterRequest): Response<List<Counter>> =
        counterService.deleteCounter(id)

    override suspend fun incrementCounter(id: IncrementCounterRequest): Response<List<Counter>> =
        counterService.incrementCounter(id)

    override suspend fun decrementCounter(id: DecrementCounterRequest): Response<List<Counter>> =
        counterService.decrementCounter(id)
}