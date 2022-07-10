package com.cornershop.counterstest.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class TextInputUseCase @Inject constructor() {
    operator fun invoke(onTextChanged: Flow<String>): Flow<TextInputState> =
        onTextChanged.map { text ->
            TextInputState(
                text = text
            )
        }

    internal data class TextInputState(
        val text: String
    )
}
