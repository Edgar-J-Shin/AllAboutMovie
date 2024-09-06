package com.dcs.presentation.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.dcs.domain.usecase.GetPopularPeopleUseCase
import com.dcs.presentation.core.model.PersonUiState
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.ui.lifecycle.launch
import com.dcs.presentation.core.ui.viewmodel.EventDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    getPopularPeopleUseCase: GetPopularPeopleUseCase,
) : ViewModel(),
    EventDelegate<PeopleEffect, PeopleUiEvent> by EventDelegate.EventDelegateImpl() {

    val popularPeople = getPopularPeopleUseCase()
        .map {
            it.map { person ->
                person.toUiState()
            }
        }
        .cachedIn(viewModelScope)

    override fun dispatchEvent(event: PeopleUiEvent) {
        when (event) {
            is PeopleUiEvent.NavigateToDetail -> {
                navigateToDetail(event.state)
            }
        }
    }

    private fun navigateToDetail(state: PersonUiState) {
        launch {
            emitEffect(
                PeopleEffect.NavigateToDetail(
                    personId = state.id
                )
            )
        }
    }
}
