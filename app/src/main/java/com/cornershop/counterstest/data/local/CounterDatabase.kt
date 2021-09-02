package com.cornershop.counterstest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CounterEntity::class], version = 1, exportSchema = false)
abstract class CounterDatabase : RoomDatabase() {

    abstract fun counterDao(): CounterDao

    companion object {
        const val DB_NAME = "CounterDatabase.db"
    }
}