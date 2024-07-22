package com.dcs.presentation.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dcs.domain.usecase.GetSearchMultiUseCase
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.state.UiState
import com.dcs.presentation.core.state.asUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSearchMultiUseCase: GetSearchMultiUseCase,
) : ViewModel() {

    var queryText by mutableStateOf("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResult = snapshotFlow { queryText }
        .debounce(SEARCH_TIMEOUT_MILLIS)
        .flatMapLatest { query ->
            when {
                query.isNotEmpty() ->
                    getSearchMultiUseCase(query)
                        .cachedIn(viewModelScope)
                        .map { pagingData -> pagingData.map { movieEntity -> movieEntity.toUiState() } }
                else -> flowOf(PagingData.empty())
            }
        }
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            initialValue = UiState.Success(PagingData.empty()),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    fun onSearchTextChange(newText: String) {
        queryText = newText
    }

    fun onSearchTextClear() {
        queryText = ""
    }

    companion object {
        private const val SEARCH_TIMEOUT_MILLIS = 500L
    }
}
