package com.cornershop.counterstest.data.local

import com.cornershop.counterstest.data.mapper.CounterEntityMapper
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: CounterDao, private val mapper: CounterEntityMapper) {

    suspend fun getCounters(): List<Counter> = mapper.mapFromEntityList(dao.getCounters())

    suspend fun createCounters(counters: List<Counter>) = dao.insertAll(mapper.mapToEntityList(counters))

    suspend fun createCounter(counter: Counter) = dao.insert(mapper.mapToEntity(counter))
}