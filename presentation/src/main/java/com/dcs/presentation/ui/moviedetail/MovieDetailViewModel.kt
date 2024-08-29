package com.dcs.presentation.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dcs.domain.model.MovieId
import com.dcs.domain.usecase.GetMovieByIdUseCase
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.ui.state.UiState
import com.dcs.presentation.core.ui.state.asUiState
import com.dcs.presentation.core.ui.lifecycle.launch
import com.dcs.presentation.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getMovieByIdUseCase: GetMovieByIdUseCase,
) : ViewModel() {

    private val movieId: Int =
        savedStateHandle[Screen.MOVIE_ID_SAVED_STATE_KEY] ?: error("Movie Id not found")

    @OptIn(ExperimentalCoroutinesApi::class)
    val movie = flowOf(movieId)
        .map {
            MovieId(it)
        }
        .flatMapLatest { movieId ->
            getMovieByIdUseCase(movieId = movieId)
                .map {
                    it.toUiState()
                }
        }
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading,
        )

    private val _effect = MutableSharedFlow<MovieDetailEffect>()
    val effect = _effect.asSharedFlow()

    private fun navigateBack() {
        launch {
            _effect.emit(MovieDetailEffect.NavigateBack)
        }
    }

    fun dispatchEvent(event: MovieDetailUiEvent) {
        when (event) {
            is MovieDetailUiEvent.NavigateBack -> {
                navigateBack()
            }
        }
    }
}
