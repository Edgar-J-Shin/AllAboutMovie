package com.dcs.presentation.ui.persondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dcs.domain.usecase.GetPersonDetailUseCase
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.model.UiState
import com.dcs.presentation.core.model.asUiState
import com.dcs.presentation.ui.Screen.Companion.PERSON_DETAIL_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPersonDetailUseCase: GetPersonDetailUseCase,
) : ViewModel() {

    private val personId = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = personId
        .filterNotNull()
        .flatMapLatest {
            getPersonDetailUseCase(it)
        }
        .map { it.toUiState() }
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading,
        )

    init {
        val id =
            savedStateHandle.get<Long>(PERSON_DETAIL_ID_KEY) ?: error("Person ID not found")
        personId.update { id }
    }
}
