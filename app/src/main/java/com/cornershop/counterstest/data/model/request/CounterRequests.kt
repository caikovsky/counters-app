package com.cornershop.counterstest.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateCounterRequest(
    val title: String
)

@Serializable
data class IncrementCounterRequest(
    val id: String
)

@Serializable
data class DecrementCounterRequest(
    val id: String
)

@Serializable
data class DeleteCounterRequest(
    val id: String
)
