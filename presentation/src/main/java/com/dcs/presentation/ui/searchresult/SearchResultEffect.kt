package com.dcs.presentation.ui.searchresult

sealed interface SearchResultEffect {
    data object NavigateBack : SearchResultEffect
}
