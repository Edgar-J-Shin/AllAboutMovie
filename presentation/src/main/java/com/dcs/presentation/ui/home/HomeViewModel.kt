package com.dcs.presentation.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dcs.domain.usecase.DeleteSearchKeywordAllUseCase
import com.dcs.domain.usecase.DeleteSearchKeywordUseCase
import com.dcs.domain.usecase.GetSearchKeywordsUseCase
import com.dcs.presentation.core.model.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSearchKeywordsUseCase: GetSearchKeywordsUseCase,
    private val deleteSearchKeywordUseCase: DeleteSearchKeywordUseCase,
    private val deleteSearchKeywordAllUseCase: DeleteSearchKeywordAllUseCase,
) : ViewModel() {

    var queryText by mutableStateOf("")

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect = _effect.asSharedFlow()

    val searchHistory = getSearchKeywordsUseCase()
        .map { keywordEntities ->
            keywordEntities
                .reversed()
                .map { it.toUiState() }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    private fun changeSearchText(newText: String) {
        queryText = newText
    }

    private fun clearSearchText() {
        queryText = ""
    }

    private fun search(query: String) {
        queryText = query

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
