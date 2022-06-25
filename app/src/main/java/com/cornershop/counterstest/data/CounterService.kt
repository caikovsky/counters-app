package com.cornershop.counterstest.data

import com.cornershop.counterstest.data.model.request.CreateCounterRequest
import com.cornershop.counterstest.data.model.request.DecrementCounterRequest
import com.cornershop.counterstest.data.model.request.DeleteCounterRequest
import com.cornershop.counterstest.data.model.request.IncrementCounterRequest
import com.cornershop.counterstest.data.model.response.CounterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface CounterService {

    @GET("counters")
    suspend fun getCounters(): List<CounterResponse>

    @POST("counter")
    suspend fun createCounter(@Body title: CreateCounterRequest): List<CounterResponse>

    @HTTP(method = "DELETE", path = "counter", hasBody = true)
    suspend fun deleteCounter(@Body id: DeleteCounterRequest): List<CounterResponse>

    @POST("counter/inc")
    suspend fun incrementCounter(@Body id: IncrementCounterRequest): List<CounterResponse>

    @POST("counter/dec")
    suspend fun decrementCounter(@Body id: DecrementCounterRequest): List<CounterResponse>
}
