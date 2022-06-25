package com.cornershop.counterstest.data.repository

import com.cornershop.counterstest.data.CounterService
import com.cornershop.counterstest.data.model.*
import com.cornershop.counterstest.data.model.request.CreateCounterRequest
import com.cornershop.counterstest.data.model.request.DecrementCounterRequest
import com.cornershop.counterstest.data.model.request.DeleteCounterRequest
import com.cornershop.counterstest.data.model.request.IncrementCounterRequest
import com.cornershop.counterstest.domain.CounterRepository
import javax.inject.Inject

class CounterRepositoryImpl @Inject constructor(
    private val counterService: CounterService
) : CounterRepository {

    override suspend fun getCounters(): List<GetCounterResponse> = counterService.getCounters()

    override suspend fun createCounter(request: CreateCounterRequest): List<CreateCounterResponse> =
        counterService.createCounter(request)

    override suspend fun deleteCounter(request: DeleteCounterRequest): List<DeleteCounterResponse> =
        counterService.deleteCounter(request)

    override suspend fun incrementCounter(request: IncrementCounterRequest): List<IncrementCounterResponse> =
        counterService.incrementCounter(request)

    override suspend fun decrementCounter(request: DecrementCounterRequest): List<DecrementCounterResponse> =
        counterService.decrementCounter(request)
}
