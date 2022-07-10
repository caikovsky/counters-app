package com.cornershop.counterstest.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CounterResponse(
    val count: Int,
    val id: String,
    val title: String
)
