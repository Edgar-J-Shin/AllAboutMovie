package com.dcs.presentation.ui.persondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.usecase.GetPersonDetailUseCase
import com.dcs.presentation.core.model.MediaTypeUiState
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.ui.state.UiState
import com.dcs.presentation.core.ui.state.asUiState
import com.dcs.presentation.core.ui.viewmodel.EventDelegate
import com.dcs.presentation.ui.Screen.Companion.PERSON_DETAIL_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPersonDetailUseCase: GetPersonDetailUseCase,
) : ViewModel(),
    EventDelegate<PersonDetailEffect, PersonDetailUiEvent> by EventDelegate.EventDelegateImpl() {

    private val personId =
        savedStateHandle.get<Int>(PERSON_DETAIL_ID_KEY) ?: error("Person ID not found")

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = flowOf(personId)
        .filterNotNull()
        .flatMapLatest {
            getPersonDetailUseCase(it)
        }
        .map { it.toUiState() }
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading,
        )

    override fun dispatchEvent(event: PersonDetailUiEvent) {
        when (event) {
            is PersonDetailUiEvent.OnKnownForCardClick -> {
                navigateToMediaDetail(event.id, event.mediaType)
            }

            is PersonDetailUiEvent.OnActingCardClick -> {
                navigateToMediaDetail(event.id, event.mediaType)
            }

            is PersonDetailUiEvent.OnProductionCardClick -> {
                navigateToMediaDetail(event.id, event.mediaType)
            }

            PersonDetailUiEvent.OnNavigationBackButtonClick -> {
                navigateUp()
            }
        }
    }

    private fun navigateUp() {
        viewModelScope.launch {
            emitEffect(PersonDetailEffect.NavigateUp)
        }
    }

    private fun navigateToMediaDetail(
        id: MediaContentId,
        mediaType: MediaTypeUiState,
    ) {
        viewModelScope.launch {
            when (mediaType) {
                MediaTypeUiState.MOVIE -> {
                    emitEffect(
                        PersonDetailEffect.NavigateToMovieDetail(
                            movieId = id.value
                        )
                    )
                }

                MediaTypeUiState.TV -> {
                    emitEffect(
                        PersonDetailEffect.NavigateToTvShowDetail(
                            tvShowId = id.value
                        )
                    )
                }
            }
        }

    }
}
