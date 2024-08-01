package com.dcs.presentation.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dcs.domain.usecase.GetUserUseCase
import com.dcs.presentation.core.model.SettingUiState
import com.dcs.presentation.core.model.UserProfile
import com.dcs.presentation.core.state.UiState
import com.dcs.presentation.core.state.asUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val createRequestTokenUseCase: CreateRequestTokenUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _effect = MutableSharedFlow<SettingEffect>()
    val effect = _effect.asSharedFlow()

    private val _state =
        MutableStateFlow<UiState<SettingUiState>>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        checkIfUserLoggedIn()
    }

    private fun checkIfUserLoggedIn() {
        viewModelScope.launch {
            getUserUseCase()
                .map { user ->
                    SettingUiState(UserProfile(user))
                }
                .asUiState()
                .onEach { state ->
                    _state.update { state }
                }
                .launchIn(this)
        }
    }

    fun dispatch(event: SettingEvent) {
        when (event) {
            SettingEvent.SignIn -> {
                createRequestTokenAndNavigateToSignIn()
            }

            is SettingEvent.SignOut -> {
                // TODO: Sign out
            }
    private fun createRequestTokenAndNavigateToSignIn() {
        viewModelScope.launch {
            createRequestTokenUseCase()
                .onStart {
                    val uiState: SettingUiState =
                        (_state.value as? UiState.Success)?.data ?: return@onStart
                    _state.update { UiState.Success(uiState.copy(loading = true)) }
                }
                .onCompletion {
                    val uiState: SettingUiState =
                        (_state.value as? UiState.Success)?.data ?: return@onCompletion
                    _state.update { UiState.Success(uiState.copy(loading = false)) }
                }
                .catch { throwable ->
                    if (throwable.message != null) {
                        _error.value = throwable.message
                    }
                }
                .collect { requestToken ->
                    // TODO: Navigate to sign in
                    emitEffect(SettingEffect.SignIn(requestToken))
                }
        }
    }

    private fun emitEffect(effect: SettingEffect) =
        viewModelScope.launch {
            _effect.emit(effect)
        }
}
