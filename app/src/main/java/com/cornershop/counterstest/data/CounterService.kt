package com.cornershop.counterstest.data

import com.cornershop.counterstest.data.model.*
import com.cornershop.counterstest.data.model.request.CreateCounterRequest
import com.cornershop.counterstest.data.model.request.DecrementCounterRequest
import com.cornershop.counterstest.data.model.request.DeleteCounterRequest
import com.cornershop.counterstest.data.model.request.IncrementCounterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface CounterService {

    @GET("counters")
    suspend fun getCounters(): List<GetCounterResponse>

    @POST("counter")
    suspend fun createCounter(@Body title: CreateCounterRequest): List<CreateCounterResponse>

    @HTTP(method = "DELETE", path = "counter", hasBody = true)
    suspend fun deleteCounter(@Body id: DeleteCounterRequest): List<DeleteCounterResponse>

    @POST("counter/inc")
    suspend fun incrementCounter(@Body id: IncrementCounterRequest): List<IncrementCounterResponse>

    @POST("counter/dec")
    suspend fun decrementCounter(@Body id: DecrementCounterRequest): List<DecrementCounterResponse>
}
