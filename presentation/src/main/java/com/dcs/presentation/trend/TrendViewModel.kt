package com.dcs.presentation.trend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dcs.domain.usecase.GetMoviesByTopRatedUseCase
import com.dcs.domain.usecase.GetMoviesByTrendingUseCase
import com.dcs.presentation.core.model.MovieItemUiState
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.state.MovieTrendType
import com.dcs.presentation.core.state.UiState
import com.dcs.presentation.core.state.asUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendViewModel @Inject constructor(
    getMoviesByTrendingUseCase: GetMoviesByTrendingUseCase,
    getMoviesByTopRatedUseCase: GetMoviesByTopRatedUseCase
) : ViewModel() {

    private var _movieTrendType = MutableStateFlow(MovieTrendType.DAY)
    val movieTrendType = _movieTrendType.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    internal val moviesByTrending = movieTrendType.flatMapLatest { type ->
        getMoviesByTrendingUseCase(type.toString())
            .cachedIn(viewModelScope)
            .map { pagingData -> pagingData.map { movieEntity -> movieEntity.toUiState() } }
    }
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Success(MutableStateFlow(PagingData.empty())),
        )

    val moviesByTopRated: StateFlow<UiState<StateFlow<PagingData<MovieItemUiState>>>> =
        getMoviesByTopRatedUseCase()
            .cachedIn(viewModelScope)
            .map { pagingData -> pagingData.map { movieEntity -> movieEntity.toUiState() } }
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
}