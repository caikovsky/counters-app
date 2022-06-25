package com.cornershop.counterstest.data.model

import com.google.gson.annotations.SerializedName

data class CreateCounterResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String
)