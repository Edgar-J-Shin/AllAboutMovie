package com.dcs.presentation.core.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

@Stable
data class SearchUiState(
    val searchKeywords: List<KeywordUiState>,
    val query: KeywordUiState,
) {
    fun queryNotEmpty() = query.keyword.isNotEmpty()
}

class SearchUiStateProvider : PreviewParameterProvider<Pair<SearchUiState, Boolean>> {

    override val values: Sequence<Pair<SearchUiState, Boolean>>
        get() = sequenceOf(
            SearchUiState(
                searchKeywords = emptyList(),
                query = KeywordUiState(""),
            ) to false,
            SearchUiState(
                searchKeywords = listOf(
                    KeywordUiState("test"),
                    KeywordUiState("test1"),
                    KeywordUiState("test2"),
                    KeywordUiState("test3"),
                    KeywordUiState("test4"),
                    KeywordUiState("test5"),
                    KeywordUiState("test6"),
                    KeywordUiState("test7"),
                    KeywordUiState("test8"),
                ),
                query = KeywordUiState("test"),
            ) to true,
        )
}
