package com.cornershop.counterstest.data

import com.cornershop.counterstest.data.request.CreateCounterRequest
import com.cornershop.counterstest.data.request.DecrementCounterRequest
import com.cornershop.counterstest.data.request.DeleteCounterRequest
import com.cornershop.counterstest.data.request.IncrementCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface CounterService {

    @GET("counters")
    suspend fun getCounters(): Response<List<Counter>>

    @POST("counter")
    suspend fun createCounter(@Body title: CreateCounterRequest): Response<List<Counter>>

    @DELETE("counter")
    suspend fun deleteCounter(@Body id: DeleteCounterRequest): List<Counter>

    @POST("counter/inc")
    suspend fun incrementCounter(@Body id: IncrementCounterRequest): List<Counter>

    @POST("counter/dec")
    suspend fun decrementCounter(@Body id: DecrementCounterRequest): List<Counter>
}