package com.cornershop.counterstest.data.local

import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: CounterDao) {

    fun getCounters(): List<Counter> = dao.getCounters()

    suspend fun createCounters(counters: List<Counter>) = dao.insertAll(counters)
}