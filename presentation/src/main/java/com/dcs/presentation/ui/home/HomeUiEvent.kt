package com.dcs.presentation.ui.home

sealed interface HomeUiEvent {

    data class OnSearchTextChanged(val query: String) : HomeUiEvent

    data object OnClearSearchTextClick : HomeUiEvent

    data class OnSearch(val keyword: String) : HomeUiEvent

    data class OnDeleteHistoryClick(val keyword: String) : HomeUiEvent

    data object OnDeleteAllHistoryClick : HomeUiEvent

}
