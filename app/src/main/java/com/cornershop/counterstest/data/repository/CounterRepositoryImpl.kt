package com.cornershop.counterstest.data.repository

import com.cornershop.counterstest.data.CounterService
import com.cornershop.counterstest.data.model.response.CounterResponse
import com.cornershop.counterstest.domain.CounterRepository
import javax.inject.Inject

class CounterRepositoryImpl @Inject constructor(
    private val counterService: CounterService
) : CounterRepository {

    override suspend fun getCounters(): List<CounterResponse> = counterService.getCounters()

    override suspend fun createCounter(request: String): List<CounterResponse> =
        counterService.createCounter(request)

    override suspend fun deleteCounter(request: String): List<CounterResponse> =
        counterService.deleteCounter(request)

    override suspend fun incrementCounter(request: String): List<CounterResponse> =
        counterService.incrementCounter(request)

    override suspend fun decrementCounter(request: String): List<CounterResponse> =
        counterService.decrementCounter(request)
}
