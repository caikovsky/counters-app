package com.cornershop.counterstest.data.repository

import com.cornershop.counterstest.data.CounterService
import com.cornershop.counterstest.data.model.request.CreateCounterRequest
import com.cornershop.counterstest.data.model.request.DecrementCounterRequest
import com.cornershop.counterstest.data.model.request.DeleteCounterRequest
import com.cornershop.counterstest.data.model.request.IncrementCounterRequest
import com.cornershop.counterstest.data.model.response.CounterResponse
import com.cornershop.counterstest.domain.CounterRepository
import javax.inject.Inject

class CounterRepositoryImpl @Inject constructor(
    private val counterService: CounterService
) : CounterRepository {

    override suspend fun getCounters(): List<CounterResponse> = counterService.getCounters()

    override suspend fun createCounter(title: String): List<CounterResponse> =
        counterService.createCounter(title.toCreateRequest())

    override suspend fun deleteCounter(request: String): List<CounterResponse> =
        counterService.deleteCounter(request.toDeleteRequest())

    override suspend fun incrementCounter(request: String): List<CounterResponse> =
        counterService.incrementCounter(request.toIncrementRequest())

    override suspend fun decrementCounter(request: String): List<CounterResponse> =
        counterService.decrementCounter(request.toDecrementRequest())
}

private fun String.toCreateRequest() = CreateCounterRequest(title = this)
private fun String.toDeleteRequest() = DeleteCounterRequest(id = this)
private fun String.toIncrementRequest() = IncrementCounterRequest(id = this)
private fun String.toDecrementRequest() = DecrementCounterRequest(id = this)
