package com.dcs.presentation.ui.searchresult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.usecase.GetSearchContentsUseCase
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.ui.lifecycle.launch
import com.dcs.presentation.core.ui.viewmodel.EventDelegate
import com.dcs.presentation.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getSearchContentsUseCase: GetSearchContentsUseCase,
) : ViewModel(),
    EventDelegate<SearchResultEffect, SearchResultUiEvent> by EventDelegate.EventDelegateImpl() {

    private val keyword: String =
        savedStateHandle[Screen.SEARCH_RESULT_KEYWORD] ?: error("Search result keyword not found")

    val searchResult = getSearchContentsUseCase(keyword)
        .map { pagingData -> pagingData.map { movie -> movie.toUiState() } }
        .cachedIn(viewModelScope)

    private fun navigateBack() {
        launch {
            emitEffect(SearchResultEffect.NavigateBack)
        }
    }

    private fun navigateToMovieDetails(mediaContentId: MediaContentId) {
        launch {
            emitEffect(
                SearchResultEffect.NavigateToMovieDetails(
                    mediaContentId = mediaContentId
                )
            )
        }
    }

    override fun dispatchEvent(event: SearchResultUiEvent) {
        when (event) {
            is SearchResultUiEvent.OnNavigationBackClick -> {
                navigateBack()
            }

            is SearchResultUiEvent.OnMediaItemClick -> {
                navigateToMovieDetails(
                    mediaContentId = event.mediaContentId
                )
            }
        }
    }
}
