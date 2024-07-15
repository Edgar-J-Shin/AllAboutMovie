package com.dcs.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor() : ViewModel() {

    private val _effect = MutableSharedFlow<SettingEffect>()
    val effect = _effect.asSharedFlow()

    fun dispatch(event: SettingEvent) {
        when (event) {
            SettingEvent.SignIn -> {
                emitEffect(SettingEffect.SignIn)
            }
        }
    }

    private fun emitEffect(effect: SettingEffect) =
        viewModelScope.launch {
            _effect.emit(effect)
        }
}
