package com.cornershop.counterstest.domain

import com.cornershop.counterstest.data.model.*
import com.cornershop.counterstest.data.model.request.CreateCounterRequest
import com.cornershop.counterstest.data.model.request.DecrementCounterRequest
import com.cornershop.counterstest.data.model.request.DeleteCounterRequest
import com.cornershop.counterstest.data.model.request.IncrementCounterRequest

interface CounterRepository {

    suspend fun getCounters(): List<GetCounterResponse>

    suspend fun createCounter(request: CreateCounterRequest): List<CreateCounterResponse>

    suspend fun deleteCounter(request: DeleteCounterRequest): List<DeleteCounterResponse>

    suspend fun incrementCounter(request: IncrementCounterRequest): List<IncrementCounterResponse>

    suspend fun decrementCounter(request: DecrementCounterRequest): List<DecrementCounterResponse>
}
