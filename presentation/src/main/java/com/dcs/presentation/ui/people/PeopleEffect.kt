package com.dcs.presentation.ui.people

sealed interface PeopleEffect {
    data class NavigateToDetail(val personId: Int) : PeopleEffect
}
