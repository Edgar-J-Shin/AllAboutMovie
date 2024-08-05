package com.dcs.presentation.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.dcs.domain.usecase.GetPopularPeopleUseCase
import com.dcs.presentation.core.model.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    getPopularPeopleUseCase: GetPopularPeopleUseCase,
) : ViewModel() {

    val popularPeople = getPopularPeopleUseCase()
        .map {
            it.map { person ->
                person.toUiState()
            }
        }
        .cachedIn(viewModelScope)
}
