package com.cornershop.counterstest.domain.util

import com.cornershop.counterstest.data.model.response.CounterResponse
import com.cornershop.counterstest.domain.model.Counter

internal fun List<CounterResponse>.toDomain(): List<Counter> =
    map { item ->
        Counter(
            id = item.id,
            title = item.title,
            count = item.count
        )
    }
