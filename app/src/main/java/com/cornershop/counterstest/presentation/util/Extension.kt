package com.cornershop.counterstest.presentation.util

import com.cornershop.counterstest.domain.model.Counter as CounterDomain
import com.cornershop.counterstest.presentation.model.Counter as CounterPresentation

internal fun List<CounterDomain>.toPresentationModel(): List<CounterPresentation> =
    map { item ->
        CounterPresentation(
            id = item.id,
            title = item.title,
            count = item.count,
            selected = false
        )
    }
