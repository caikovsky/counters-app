package com.cornershop.counterstest.presentation.model

internal data class Counter(
    val id: String = "",
    val title: String = "",
    var count: Int = 0,
    var selected: Boolean = false
)
