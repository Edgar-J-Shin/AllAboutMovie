package com.dcs.presentation.core.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

@Stable
data class SearchUiState(
    val searchKeywords: List<KeywordUiState>,
    val query: KeywordUiState,
    val searchActive: MutableState<Boolean>,
) {
    fun queryNotEmpty() = query.keyword.isNotEmpty()
}

class SearchUiStateProvider : PreviewParameterProvider<SearchUiState> {

    override val values: Sequence<SearchUiState>
        get() = sequenceOf(
            SearchUiState(
                searchKeywords = emptyList(),
                query = KeywordUiState(""),
                searchActive = mutableStateOf(false)
            ),
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
                searchActive = mutableStateOf(true)
            ),
        )
}
