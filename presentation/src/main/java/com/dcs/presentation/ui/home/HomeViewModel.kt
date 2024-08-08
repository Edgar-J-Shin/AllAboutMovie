package com.dcs.presentation.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dcs.domain.usecase.GetSearchContentsUseCase
import com.dcs.domain.usecase.GetSearchKeywordsUseCase
import com.dcs.presentation.core.model.MovieItemUiState
import com.dcs.presentation.core.model.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getSearchContentsUseCase: GetSearchContentsUseCase,
    getSearchKeywordsUseCase: GetSearchKeywordsUseCase,
) : ViewModel() {

    var queryText by mutableStateOf("")

    private val _searchResult: MutableStateFlow<PagingData<MovieItemUiState>> = MutableStateFlow(PagingData.empty())
    val searchResult = _searchResult.asStateFlow()

    val searchHistory = getSearchKeywordsUseCase()
        .map { it.map { it.toUiState() } }
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
            if (queryText.isNotEmpty()) {
                getSearchContentsUseCase(query)
                    .cachedIn(viewModelScope)
                    .map { pagingData -> pagingData.map { movieEntity -> movieEntity.toUiState() } }
                    .collect {
                        _searchResult.emit(it)
                    }
            }
        }
    }

    private fun deleteHistory(keyword: String) {

    }

    private fun deleteAllHistory() {

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
