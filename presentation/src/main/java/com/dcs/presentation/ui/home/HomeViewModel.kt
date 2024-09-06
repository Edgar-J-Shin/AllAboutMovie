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
import com.dcs.presentation.core.ui.viewmodel.EventDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
) : ViewModel(),
    EventDelegate<HomeEffect, HomeUiEvent> by EventDelegate.EventDelegateImpl() {

    private val searchKeywords = getSearchKeywordsUseCase(KEYWORD_COUNT_LIMIT)
        .map { keywordEntities -> keywordEntities.map { it.toUiState() } }

    private val _searchUiState = MutableStateFlow(INIT_SEARCH_UI_STATE)
    val searchUiState = combine(_searchUiState.asStateFlow(), searchKeywords) { searchUiState, searchKeywords ->
        searchUiState.copy(searchKeywords = searchKeywords)
    }.stateIn(
        scope = viewModelScope,
        initialValue = INIT_SEARCH_UI_STATE,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    private fun changeSearchText(newText: String) {
        _searchUiState.update { it.copy(query = KeywordUiState(newText)) }
    }

    private fun clearSearchText() {
        _searchUiState.update { it.copy(query = KeywordUiState("")) }
    }

    private fun search(query: String) {
        launch {
            emitEffect(
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

    override fun dispatchEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnSearchTextChanged -> {
                changeSearchText(event.query)
            }

            HomeUiEvent.OnClearSearchTextClick -> {
                clearSearchText()
            }

            is HomeUiEvent.OnSearch -> {
                search(event.keyword)
            }

            is HomeUiEvent.OnDeleteHistoryClick -> {
                deleteHistory(event.keyword)
            }

            HomeUiEvent.OnDeleteAllHistoryClick -> {
                deleteAllHistory()
            }
        }
    }

    companion object {
        private const val KEYWORD_COUNT_LIMIT = 20

        private val INIT_SEARCH_UI_STATE = SearchUiState(
            searchKeywords = emptyList(),
            query = KeywordUiState("")
        )
    }
}
