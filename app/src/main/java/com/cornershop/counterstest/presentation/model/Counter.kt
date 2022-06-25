package com.cornershop.counterstest.presentation.model

internal data class Counter(
    val id: String,
    val title: String,
    var count: Int,
    var selected: Boolean
)
