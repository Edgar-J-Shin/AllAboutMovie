package com.dcs.presentation.ui.searchresult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.dcs.domain.model.MovieId
import com.dcs.domain.usecase.GetSearchContentsUseCase
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.ui.lifecycle.launch
import com.dcs.presentation.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getSearchContentsUseCase: GetSearchContentsUseCase,
) : ViewModel() {

    private val _effect = MutableSharedFlow<SearchResultEffect>()
    val effect = _effect.asSharedFlow()

    private val keyword: String =
        savedStateHandle[Screen.SEARCH_RESULT_KEYWORD] ?: error("Search result keyword not found")

    val searchResult = getSearchContentsUseCase(keyword)
        .map { pagingData -> pagingData.map { movieEntity -> movieEntity.toUiState() } }
        .cachedIn(viewModelScope)

    private fun navigateBack() {
        launch {
            _effect.emit(SearchResultEffect.NavigateBack)
        }
    }

    private fun navigateToMovieDetails(movieId: MovieId) {
        launch {
            _effect.emit(
                SearchResultEffect.NavigateToMovieDetails(
                    movieId = movieId
                )
            )
        }
    }

    fun dispatchEvent(event: SearchResultUiEvent) {
        when (event) {
            is SearchResultUiEvent.NavigateBack -> {
                navigateBack()
            }

            is SearchResultUiEvent.NavigateToMovieDetails -> {
                navigateToMovieDetails(
                    movieId = event.movieId
                )
            }
        }
    }
}
