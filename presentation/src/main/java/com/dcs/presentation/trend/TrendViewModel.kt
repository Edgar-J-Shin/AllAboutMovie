package com.dcs.presentation.trend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dcs.domain.usecase.GetMoviesByPopularUseCase
import com.dcs.domain.usecase.GetMoviesByTrendingUseCase
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.state.MovieFreeType
import com.dcs.presentation.core.state.MoviePopularType
import com.dcs.presentation.core.state.MovieTrendType
import com.dcs.presentation.core.state.UiState
import com.dcs.presentation.core.state.asUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TrendViewModel @Inject constructor(
    getMoviesByTrendingUseCase: GetMoviesByTrendingUseCase,
    getMoviesByPopularUseCase: GetMoviesByPopularUseCase
) : ViewModel() {

    private var _movieTrendType = MutableStateFlow(MovieTrendType.DAY)
    val movieTrendType = _movieTrendType.asStateFlow()

    private var _moviePopularType = MutableStateFlow(MoviePopularType.MOVIE)
    val moviePopularType = _moviePopularType.asStateFlow()

    private var _movieFreeType = MutableStateFlow(MovieFreeType.MOVIE)
    val movieFreeType = _movieFreeType.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    internal val moviesByTrending = movieTrendType
        .map { it.toString().lowercase(Locale.getDefault()) }
        .flatMapLatest { type ->
            getMoviesByTrendingUseCase(type)
                .cachedIn(viewModelScope)
                .map { pagingData -> pagingData.map { movieEntity -> movieEntity.toUiState() } }
        }
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Success(MutableStateFlow(PagingData.empty())),
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    internal val moviesByPopular = moviePopularType
        .map { it.toString().lowercase(Locale.getDefault()) }
        .flatMapLatest { type ->
            getMoviesByPopularUseCase(type)
                .cachedIn(viewModelScope)
                .map { pagingData -> pagingData.map { movieEntity -> movieEntity.toUiState() } }
        }
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Success(MutableStateFlow(PagingData.empty())),
        )

    fun updateMovieTrendType(movieTrendType: MovieTrendType) {
        viewModelScope.launch {
            _movieTrendType.emit(movieTrendType)
        }
    }

    fun updateMoviePopularType(moviePopularType: MoviePopularType) {
        viewModelScope.launch {
            _moviePopularType.emit(moviePopularType)
        }
    }

    fun updateMovieFreeType(movieFreeType: MovieFreeType) {
        viewModelScope.launch {
            _movieFreeType.emit(movieFreeType)
        }
    }
}