package com.cornershop.counterstest.data

import android.content.Context
import androidx.room.Room
import com.cornershop.counterstest.data.local.CounterDatabase
import com.cornershop.counterstest.data.local.CounterDatabase.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideCounterDao(db: CounterDatabase) = db.counterDao()
}