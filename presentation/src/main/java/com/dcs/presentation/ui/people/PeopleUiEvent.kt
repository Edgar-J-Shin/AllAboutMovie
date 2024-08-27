package com.dcs.presentation.ui.people

import com.dcs.presentation.core.model.PersonUiState

sealed interface PeopleUiEvent {
    data class NavigateToDetail(val state: PersonUiState) : PeopleUiEvent
}
