package com.cornershop.counterstest.data.request

data class CreateCounterRequest(val title: String)
data class DeleteCounterRequest(val id: String)
data class DecrementCounterRequest(val id: String)
data class IncrementCounterRequest(val id: String)