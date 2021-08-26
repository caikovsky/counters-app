package com.cornershop.counterstest.data.repository

import com.cornershop.counterstest.data.remote.CounterDataSource
import com.cornershop.counterstest.domain.model.Counter

class CounterRepository(private val dataSource: CounterDataSource) {

    suspend fun getCounters(): List<Counter> = dataSource.getCounters()

    suspend fun createCounter(title: String): Counter = dataSource.createCounter(title)

    suspend fun deleteCounter(id: String): Counter = dataSource.deleteCounter(id)

    suspend fun incrementCounter(id: String): Counter = dataSource.incrementCounter(id)

    suspend fun decrementCounter(id: String): Counter = dataSource.decrementCounter(id)
}