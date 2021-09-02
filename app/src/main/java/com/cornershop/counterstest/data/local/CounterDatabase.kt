package com.cornershop.counterstest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cornershop.counterstest.domain.model.Counter

@Database(entities = [Counter::class], version = 1)
abstract class CounterDatabase : RoomDatabase() {

    abstract fun counterDao(): CounterDao

    companion object {
        const val DB_NAME = "CounterDatabase.db"
    }
}