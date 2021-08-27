package com.cornershop.counterstest.domain.usecases.delete

import com.cornershop.counterstest.data.repository.CounterRepository
import com.cornershop.counterstest.data.request.DeleteCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class DeleteCounterUseCaseImpl @Inject constructor(private val counterRepository: CounterRepository) :
    DeleteCounterUseCase {
    override suspend fun invoke(id: DeleteCounterRequest): List<Counter> {
        return counterRepository.deleteCounter(id)
    }
}