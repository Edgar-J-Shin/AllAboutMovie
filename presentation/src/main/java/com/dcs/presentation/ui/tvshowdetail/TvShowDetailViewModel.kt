package com.dcs.presentation.ui.tvshowdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.usecase.GetTvShowByIdUseCase
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.ui.lifecycle.launch
import com.dcs.presentation.core.ui.state.UiState
import com.dcs.presentation.core.ui.state.asUiState
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
class TvShowDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTvShowByIdUseCase: GetTvShowByIdUseCase,
) : ViewModel() {

    private val tvShowId: Int =
        savedStateHandle[Screen.TV_SHOW_ID_SAVED_STATE_KEY] ?: error("Movie Id not found")

    @OptIn(ExperimentalCoroutinesApi::class)
    val tvShow = flowOf(tvShowId)
        .map {
            MediaContentId(it)
        }
        .flatMapLatest { mediaContentId ->
            getTvShowByIdUseCase(mediaContentId = mediaContentId)
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

    private val _effect = MutableSharedFlow<TvShowDetailEffect>()
    val effect = _effect.asSharedFlow()

    private fun navigateBack() {
        launch {
            _effect.emit(TvShowDetailEffect.NavigateBack)
        }
    }

    fun dispatchEvent(event: TvShowDetailUiEvent) {
        when (event) {
            is TvShowDetailUiEvent.NavigateBack -> {
                navigateBack()
            }
        }
    }
}
