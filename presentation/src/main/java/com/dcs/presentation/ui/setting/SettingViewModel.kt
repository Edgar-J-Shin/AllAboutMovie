package com.dcs.presentation.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dcs.presentation.core.model.SettingUiState
import com.dcs.presentation.core.model.UserProfile
import com.dcs.presentation.core.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor() : ViewModel() {

    private val _effect = MutableSharedFlow<SettingEffect>()
    val effect = _effect.asSharedFlow()

    private val _state =
        MutableStateFlow<UiState<SettingUiState>>(UiState.Loading)
    val state = _state.asStateFlow()


    fun dispatch(event: SettingEvent) {
        when (event) {
            SettingEvent.SignIn -> {
                emitEffect(SettingEffect.SignIn)
            }

            is SettingEvent.SignOut -> {
                // TODO: Sign out
            }
        }
    }

    private fun emitEffect(effect: SettingEffect) =
        viewModelScope.launch {
            _effect.emit(effect)
        }
}
