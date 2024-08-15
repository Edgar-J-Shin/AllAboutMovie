package com.dcs.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dcs.domain.usecase.DeleteSearchKeywordAllUseCase
import com.dcs.domain.usecase.DeleteSearchKeywordUseCase
import com.dcs.domain.usecase.GetSearchKeywordsUseCase
import com.dcs.presentation.core.model.KeywordUiState
import com.dcs.presentation.core.model.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSearchKeywordsUseCase: GetSearchKeywordsUseCase,
    private val deleteSearchKeywordUseCase: DeleteSearchKeywordUseCase,
    private val deleteSearchKeywordAllUseCase: DeleteSearchKeywordAllUseCase,
) : ViewModel() {

    private val _keyword = MutableStateFlow(KeywordUiState(keyword = ""))
    val keyword = _keyword.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect = _effect.asSharedFlow()

    val searchHistory = getSearchKeywordsUseCase()
        .map { keywordEntities -> keywordEntities.map { it.toUiState() } }
        .stateIn(
            scope = viewModelScope,
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    private fun changeSearchText(newText: String) {
        _keyword.update { KeywordUiState(newText) }
    }

    private fun clearSearchText() {
        _keyword.update { KeywordUiState("") }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            _effect.emit(HomeEffect.NavigateToSearchResult(query))
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
}
