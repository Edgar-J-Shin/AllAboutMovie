package com.dcs.presentation.ui.home

sealed interface HomeEffect {

    data class NavigateToSearchResult(val keyword: String) : HomeEffect
}
