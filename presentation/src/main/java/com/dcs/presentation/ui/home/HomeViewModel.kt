package com.dcs.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dcs.domain.usecase.DeleteSearchKeywordAllUseCase
import com.dcs.domain.usecase.DeleteSearchKeywordUseCase
import com.dcs.domain.usecase.GetSearchKeywordsUseCase
import com.dcs.presentation.core.designsystem.state.SnackbarState
import com.dcs.presentation.core.model.KeywordUiState
import com.dcs.presentation.core.model.SearchUiState
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.ui.lifecycle.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSearchKeywordsUseCase: GetSearchKeywordsUseCase,
    private val deleteSearchKeywordUseCase: DeleteSearchKeywordUseCase,
    private val deleteSearchKeywordAllUseCase: DeleteSearchKeywordAllUseCase,
) : ViewModel() {

    private val searchKeywords = getSearchKeywordsUseCase(KEYWORD_COUNT_LIMIT)
        .map { keywordEntities -> keywordEntities.map { it.toUiState() } }

    private val _searchUiState = MutableStateFlow(
        SearchUiState(
            searchKeywords = emptyList(),
            query = KeywordUiState("")
        )
    )
    val searchUiState = combine(_searchUiState.asStateFlow(), searchKeywords) { searchUiState, searchKeywords ->
        searchUiState.copy(searchKeywords = searchKeywords)
    }.stateIn(
        scope = viewModelScope,
        initialValue = SearchUiState(
            searchKeywords = emptyList(),
            query = KeywordUiState("")
        ),
        started = SharingStarted.WhileSubscribed(5_000)
    )

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect = _effect.asSharedFlow()

    private fun changeSearchText(newText: String) {
        _searchUiState.update { it.copy(query = KeywordUiState(newText)) }
    }

    private fun clearSearchText() {
        _searchUiState.update { it.copy(query = KeywordUiState("")) }
    }

    private fun search(query: String) {
        launch {
            _effect.emit(
                if (query.isEmpty()) {
                    HomeEffect.ShowSnackbar(state = SnackbarState.SearchQueryEmptyError)
                } else {
                    HomeEffect.NavigateToSearchResult(keyword = query)
                }
            )
        }
    }

    private fun deleteHistory(keyword: String) {
        deleteSearchKeywordUseCase(keyword)
            .launchIn(viewModelScope)
    }

    private fun deleteAllHistory() {
        deleteSearchKeywordAllUseCase()
            .launchIn(viewModelScope)
    }

    fun dispatchEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.SearchTextChanged -> {
                changeSearchText(event.query)
            }

            HomeUiEvent.ClearSearchText -> {
                clearSearchText()
            }

            is HomeUiEvent.SearchText -> {
                search(event.keyword)
            }

            is HomeUiEvent.DeleteHistory -> {
                deleteHistory(event.keyword)
            }

            HomeUiEvent.DeleteAllHistory -> {
                deleteAllHistory()
            }
        }
    }

    companion object {
        private const val KEYWORD_COUNT_LIMIT = 20
    }
}
