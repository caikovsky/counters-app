package com.cornershop.counterstest.data.remote

import com.cornershop.counterstest.domain.model.Counter

interface CounterDataSource {

    suspend fun getCounters(): List<Counter>

    suspend fun createCounter(title: String): Counter

    suspend fun deleteCounter(id: String): Counter

    suspend fun incrementCounter(id: String): Counter

    suspend fun decrementCounter(id: String): Counter
}