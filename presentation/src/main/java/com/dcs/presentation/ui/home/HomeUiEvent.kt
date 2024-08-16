package com.dcs.presentation.ui.home

sealed interface HomeUiEvent {

    data class SearchTextChanged(val query: String) : HomeUiEvent

    data object ClearSearchText : HomeUiEvent

    data class SearchText(val keyword: String) : HomeUiEvent

    data class DeleteHistory(val keyword: String) : HomeUiEvent

    data object DeleteAllHistory : HomeUiEvent

}
