package com.cornershop.counterstest.data.local

import androidx.room.*

@Dao
interface CounterDao {
    @Query("SELECT * FROM counters")
    suspend fun getCounters(): List<CounterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(counterList: List<CounterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(counter: CounterEntity)

    @Delete
    suspend fun deleteCounter(counter: CounterEntity)
}