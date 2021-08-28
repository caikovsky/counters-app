package com.cornershop.counterstest.data.remote

import com.cornershop.counterstest.data.request.CreateCounterRequest
import com.cornershop.counterstest.data.request.DecrementCounterRequest
import com.cornershop.counterstest.data.request.DeleteCounterRequest
import com.cornershop.counterstest.data.request.IncrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import retrofit2.Response

interface CounterServiceHelper {

    suspend fun getCounters(): Response<List<Counter>>

    suspend fun createCounter(title: CreateCounterRequest): Response<List<Counter>>

    suspend fun deleteCounter(id: DeleteCounterRequest): List<Counter>

    suspend fun incrementCounter(id: IncrementCounterRequest): List<Counter>

    suspend fun decrementCounter(id: DecrementCounterRequest): List<Counter>
}