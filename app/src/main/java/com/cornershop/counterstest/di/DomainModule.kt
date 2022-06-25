package com.cornershop.counterstest.di

import com.cornershop.counterstest.data.CounterService
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.domain.CounterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideCounterRepository(service: CounterService): CounterRepository =
        CounterRepositoryImpl(service)
}
