package com.dcs.presentation.core.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

@Stable
data class KeywordUiState(
    val keyword: String,
)

class KeywordUiStateProvider : PreviewParameterProvider<List<KeywordUiState>> {

    override val values: Sequence<List<KeywordUiState>>
        get() = sequenceOf(
            listOf(),
        )
}
