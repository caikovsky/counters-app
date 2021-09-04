package com.cornershop.counterstest.domain.usecases.delete

import com.cornershop.counterstest.data.core.BaseApiResponse
import com.cornershop.counterstest.data.core.NetworkResult
import com.cornershop.counterstest.data.repository.CounterRepository
import com.cornershop.counterstest.data.request.DeleteCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class DeleteCounterUseCaseImpl @Inject constructor(private val counterRepository: CounterRepository) :
    DeleteCounterUseCase, BaseApiResponse() {
    override suspend fun invoke(id: DeleteCounterRequest): NetworkResult<List<Counter>> {
        return safeApiCall { counterRepository.deleteCounter(id) }
    }
}