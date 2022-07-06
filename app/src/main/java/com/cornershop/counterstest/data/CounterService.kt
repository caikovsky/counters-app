package com.cornershop.counterstest.data

import com.cornershop.counterstest.data.model.response.CounterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface CounterService {

    @GET("counters")
    suspend fun getCounters(): List<CounterResponse>

    @POST("counter")
    suspend fun createCounter(@Body title: String): List<CounterResponse>

    @HTTP(method = "DELETE", path = "counter", hasBody = true)
    suspend fun deleteCounter(@Body id: String): List<CounterResponse>

    @POST("counter/inc")
    suspend fun incrementCounter(@Body id: String): List<CounterResponse>

    @POST("counter/dec")
    suspend fun decrementCounter(@Body id: String): List<CounterResponse>
}
