package com.dcs.presentation.ui.searchresult

sealed interface SearchResultUiEvent {

    data object NavigateBack : SearchResultUiEvent
}
