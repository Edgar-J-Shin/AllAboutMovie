package com.dcs.presentation.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.dcs.domain.usecase.GetPopularPeopleUseCase
import com.dcs.presentation.core.model.PersonUiState
import com.dcs.presentation.core.model.mapper.toUiState
import com.dcs.presentation.core.ui.lifecycle.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    getPopularPeopleUseCase: GetPopularPeopleUseCase,
) : ViewModel() {

    private val _effect = MutableSharedFlow<PeopleEffect>()
    val effect = _effect.asSharedFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    val popularPeople = getPopularPeopleUseCase()
        .map {
            it.map { person ->
                person.toUiState()
            }
        }
        .cachedIn(viewModelScope)

    fun dispatchEvent(event: PeopleUiEvent) {
        when (event) {
            is PeopleUiEvent.NavigateToDetail -> {
                saveAndNavigateToDetail(event.state)
            }
        }
    }

    private fun saveAndNavigateToDetail(state: PersonUiState) {
        launch {
            _effect.emit(PeopleEffect.NavigateToDetail(state.id))
        }
    }
}
