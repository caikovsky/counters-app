package com.cornershop.counterstest.domain.model

data class Counter(
    val id: String = "",
    val title: String = "",
    var count: Int = 0
)