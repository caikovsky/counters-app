package com.cornershop.counterstest.domain

import com.cornershop.counterstest.data.model.response.CounterResponse

interface CounterRepository {

    suspend fun getCounters(): List<CounterResponse>

    suspend fun createCounter(title: String): List<CounterResponse>

    suspend fun deleteCounter(request: String): List<CounterResponse>

    suspend fun incrementCounter(request: String): List<CounterResponse>

    suspend fun decrementCounter(request: String): List<CounterResponse>
}
