package com.cornershop.counterstest.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cornershop.counterstest.domain.model.Counter

@Dao
interface CounterDao {
    @Query("SELECT * FROM list_counters")
    fun getCounters(): List<Counter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(counterList: List<Counter>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(counter: Counter)

}