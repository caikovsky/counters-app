package com.cornershop.counterstest.domain

import com.cornershop.counterstest.domain.usecases.create.CreateCounterUseCase
import com.cornershop.counterstest.domain.usecases.create.CreateCounterUseCaseImpl
import com.cornershop.counterstest.domain.usecases.dec.DecrementCounterUseCase
import com.cornershop.counterstest.domain.usecases.dec.DecrementCounterUseCaseImpl
import com.cornershop.counterstest.domain.usecases.delete.DeleteCounterUseCase
import com.cornershop.counterstest.domain.usecases.delete.DeleteCounterUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindCreateCounterUseCase(useCase: CreateCounterUseCaseImpl): CreateCounterUseCase

    @Binds
    fun bindDeleteCounterUseCase(useCase: DeleteCounterUseCaseImpl): DeleteCounterUseCase

    @Binds
    fun bindDecrementCounterUseCase(useCase: DecrementCounterUseCaseImpl): DecrementCounterUseCase
}
