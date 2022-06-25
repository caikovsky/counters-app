package com.cornershop.counterstest.data.model.response

import com.google.gson.annotations.SerializedName

data class CounterResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String
)
