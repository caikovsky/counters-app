package com.cornershop.counterstest.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cornershop.counterstest.data.local.CounterDatabase
import com.cornershop.counterstest.data.local.CounterDatabase.Companion.DB_NAME
import com.cornershop.counterstest.util.logD
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CounterDatabase::class.java,
        DB_NAME
     ).allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideCocktailDao(db: CounterDatabase) = db.counterDao()
}