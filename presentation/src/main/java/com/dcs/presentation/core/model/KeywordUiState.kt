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
            listOf(
                KeywordUiState("test"),
                KeywordUiState("test1"),
                KeywordUiState("test2"),
                KeywordUiState("test3"),
                KeywordUiState("test4"),
                KeywordUiState("a"),
                KeywordUiState("ab"),
                KeywordUiState("c"),
                KeywordUiState("asdc"),
            )
        )
}
