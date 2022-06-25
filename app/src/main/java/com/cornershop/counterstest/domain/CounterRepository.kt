package com.cornershop.counterstest.domain

import com.cornershop.counterstest.data.model.request.CreateCounterRequest
import com.cornershop.counterstest.data.model.request.DecrementCounterRequest
import com.cornershop.counterstest.data.model.request.DeleteCounterRequest
import com.cornershop.counterstest.data.model.request.IncrementCounterRequest
import com.cornershop.counterstest.data.model.response.CounterResponse

interface CounterRepository {

    suspend fun getCounters(): List<CounterResponse>

    suspend fun createCounter(request: CreateCounterRequest): List<CounterResponse>

    suspend fun deleteCounter(request: DeleteCounterRequest): List<CounterResponse>

    suspend fun incrementCounter(request: IncrementCounterRequest): List<CounterResponse>

    suspend fun decrementCounter(request: DecrementCounterRequest): List<CounterResponse>
}
