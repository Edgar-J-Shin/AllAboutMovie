package com.dcs.presentation.ui.trend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.model.MediaType
import com.dcs.domain.usecase.GetMoviesByPopularUseCase
import com.dcs.domain.usecase.GetMoviesByTrendingUseCase
import com.dcs.domain.usecase.GetMoviesByUpcomingUseCase
import com.dcs.domain.usecase.GetPopularTvShowsUseCase
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.model.mapper.toTimeWindow
import com.dcs.presentation.core.state.MoviePopularUiType
import com.dcs.presentation.core.state.MovieTrendUiType
import com.dcs.presentation.core.ui.lifecycle.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TrendViewModel @Inject constructor(
    getMoviesByTrendingUseCase: GetMoviesByTrendingUseCase,
    getMoviesByPopularUseCase: GetMoviesByPopularUseCase,
    getMoviesByUpcomingUseCase: GetMoviesByUpcomingUseCase,
    getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
) : ViewModel() {

    private var _movieTrendUiType = MutableStateFlow(MovieTrendUiType.DAY)
    private val movieTrendType = _movieTrendUiType.asStateFlow()

    private var _moviePopularUiType = MutableStateFlow(MoviePopularUiType.TV)
    private val moviePopularType = _moviePopularUiType.asStateFlow()

    private val _effect = MutableSharedFlow<TrendEffect>()
    val effect = _effect.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    internal val moviesByTrending = movieTrendType
        .map { it.toTimeWindow() }
        .flatMapLatest { timeWindow ->
            getMoviesByTrendingUseCase(MediaType.mediaType("movie"), timeWindow)
                .map { pagingData -> pagingData.map { mediaPolymorphic -> mediaPolymorphic.toUiState() } }
                .cachedIn(viewModelScope)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PagingData.empty(),
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    internal val moviesByPopular = moviePopularType
        .flatMapLatest { popularType ->
            when (popularType) {
                MoviePopularUiType.TV -> {
                    getPopularTvShowsUseCase()
                        .map { pagingData -> pagingData.map { movie -> movie.toUiState() } }
                        .cachedIn(viewModelScope)
                }

                MoviePopularUiType.MOVIE -> {
                    getMoviesByPopularUseCase()
                        .map { pagingData -> pagingData.map { movie -> movie.toUiState() } }
                        .cachedIn(viewModelScope)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PagingData.empty(),
        )

    internal val moviesByUpcoming = getMoviesByUpcomingUseCase()
        .map { pagingData ->
            pagingData.map { movie ->
                movie.toUiState()
            }
        }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PagingData.empty(),
        )

    fun updateMovieTrendType(movieTrendUiType: MovieTrendUiType) {
        launch {
            _movieTrendUiType.emit(movieTrendUiType)
        }
    }

    fun updateMoviePopularType(moviePopularUiType: MoviePopularUiType) {
        launch {
            _moviePopularUiType.emit(moviePopularUiType)
        }
    }

    private fun navigateToMovieDetails(mediaContentId: MediaContentId) {
        launch {
            _effect.emit(
                TrendEffect.NavigateToMovieDetails(
                    mediaContentId = mediaContentId
                )
            )
        }
    }

    fun dispatchEvent(event: TrendUiEvent) {
        when (event) {

            is TrendUiEvent.NavigateToMovieDetails -> {
                navigateToMovieDetails(
                    mediaContentId = event.mediaContentId
                )
            }
        }
    }
}
