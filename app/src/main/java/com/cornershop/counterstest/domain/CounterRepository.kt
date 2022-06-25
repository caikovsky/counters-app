package com.cornershop.counterstest.domain

import com.cornershop.counterstest.data.model.GetCounterResponse
import com.cornershop.counterstest.data.request.CreateCounterRequest
import com.cornershop.counterstest.data.request.DecrementCounterRequest
import com.cornershop.counterstest.data.request.DeleteCounterRequest
import com.cornershop.counterstest.data.request.IncrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import retrofit2.Response

interface CounterRepository {

    suspend fun getCounters(): List<GetCounterResponse>

    suspend fun createCounter(request: CreateCounterRequest): Response<List<Counter>>

    suspend fun deleteCounter(request: DeleteCounterRequest): Response<List<Counter>>

    suspend fun incrementCounter(request: IncrementCounterRequest): Response<List<Counter>>

    suspend fun decrementCounter(request: DecrementCounterRequest): Response<List<Counter>>
}
