package com.dcs.presentation.ui.searchresult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dcs.domain.usecase.GetSearchContentsUseCase
import com.dcs.presentation.core.model.MovieItemUiState
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.ui.lifecycle.launch
import com.dcs.presentation.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSearchContentsUseCase: GetSearchContentsUseCase,
) : ViewModel() {
    private val _searchResult: MutableStateFlow<PagingData<MovieItemUiState>> = MutableStateFlow(PagingData.empty())
    val searchResult = _searchResult.asStateFlow()

    private val _effect = MutableSharedFlow<SearchResultEffect>()
    val effect = _effect.asSharedFlow()

    private val keyword: String =
        savedStateHandle[Screen.SEARCH_RESULT_KEYWORD] ?: error("Search result keyword not found")

    private fun search(query: String) {
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                getSearchContentsUseCase(query)
                    .cachedIn(viewModelScope)
                    .map { pagingData -> pagingData.map { movieEntity -> movieEntity.toUiState() } }
                    .collect {
                        _searchResult.emit(it)
                    }
            }
        }
    }

    private fun navigateBack() {
        launch {
            _effect.emit(SearchResultEffect.NavigateBack)
        }
    }

    fun dispatchEvent(event: SearchResultUiEvent) {
        when (event) {
            is SearchResultUiEvent.NavigateBack -> {
                navigateBack()
            }
        }
    }

    init {
        search(keyword)
    }
}
