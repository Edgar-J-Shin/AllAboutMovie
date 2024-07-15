package com.dcs.presentation.setting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor() : ViewModel() {

    fun dispatch(event: SettingEvent) {
        when (event) {
            SettingEvent.SignIn -> {
            }
        }
    }
}
