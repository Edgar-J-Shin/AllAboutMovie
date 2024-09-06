package com.dcs.presentation.ui.trend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.model.MediaType
import com.dcs.domain.usecase.GetPopularMoviesUseCase
import com.dcs.domain.usecase.GetPopularTvShowsUseCase
import com.dcs.domain.usecase.GetTrendingMoviesUseCase
import com.dcs.domain.usecase.GetUpcomingMoviesUseCase
import com.dcs.presentation.core.model.PopularMovieUiType
import com.dcs.presentation.core.model.TrendingMovieUiType
import com.dcs.presentation.core.model.mapper.toTimeWindow
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.ui.lifecycle.launch
import com.dcs.presentation.core.ui.viewmodel.EventDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TrendViewModel @Inject constructor(
    getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
) : ViewModel(),
    EventDelegate<TrendEffect, TrendUiEvent> by EventDelegate.EventDelegateImpl() {

    private var _trendingMovieUiType = MutableStateFlow(TrendingMovieUiType.DAY)
    private val trendingMovieUiType = _trendingMovieUiType.asStateFlow()

    private var _popularMovieUiType = MutableStateFlow(PopularMovieUiType.TV)
    private val popularMovieUiType = _popularMovieUiType.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    internal val trendingMovies = trendingMovieUiType
        .map { it.toTimeWindow() }
        .flatMapLatest { timeWindow ->
            getTrendingMoviesUseCase(MediaType.MOVIE, timeWindow)
                .map { pagingData -> pagingData.map { mediaPolymorphic -> mediaPolymorphic.toUiState() } }
                .cachedIn(viewModelScope)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PagingData.empty(),
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    internal val popularMovies = popularMovieUiType
        .flatMapLatest { popularType ->
            when (popularType) {
                PopularMovieUiType.TV -> {
                    getPopularTvShowsUseCase()
                        .map { pagingData -> pagingData.map { tvShow -> tvShow.toUiState() } }
                        .cachedIn(viewModelScope)
                }

                PopularMovieUiType.MOVIE -> {
                    getPopularMoviesUseCase()
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

    internal val moviesByUpcoming = getUpcomingMoviesUseCase()
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

    fun updateTrendingMovieUiType(trendingMovieUiType: TrendingMovieUiType) {
        launch {
            _trendingMovieUiType.emit(trendingMovieUiType)
        }
    }

    fun updatePopularMovieUiType(popularMovieUiType: PopularMovieUiType) {
        launch {
            _popularMovieUiType.emit(popularMovieUiType)
        }
    }

    private fun navigateToMovieDetails(mediaContentId: MediaContentId) {
        launch {
            emitEffect(
                TrendEffect.NavigateToMovieDetails(
                    mediaContentId = mediaContentId
                )
            )
        }
    }

    private fun navigateToTvShowDetails(mediaContentId: MediaContentId) {
        launch {
            emitEffect(
                TrendEffect.NavigateToTvShowDetails(
                    mediaContentId = mediaContentId
                )
            )
        }
    }

    override fun dispatchEvent(event: TrendUiEvent) {
        when (event) {
            is TrendUiEvent.OnMediaItemClick -> {
                if (event.mediaType == MediaType.MOVIE) {
                    navigateToMovieDetails(
                        mediaContentId = event.mediaContentId
                    )
                } else {
                    navigateToTvShowDetails(
                        mediaContentId = event.mediaContentId
                    )
                }
            }
        }
    }
}
