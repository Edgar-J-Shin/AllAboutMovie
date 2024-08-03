package com.dcs.presentation.ui.setting

import androidx.lifecycle.ViewModel
import com.dcs.domain.usecase.CreateRequestTokenUseCase
import com.dcs.domain.usecase.GetUserUseCase
import com.dcs.domain.usecase.SignOutUseCase
import com.dcs.presentation.core.designsystem.state.SnackbarState
import com.dcs.presentation.core.model.SettingUiState
import com.dcs.presentation.core.model.UserProfile
import com.dcs.presentation.core.state.UiState
import com.dcs.presentation.core.state.asUiState
import com.dcs.presentation.core.ui.lifecycle.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val createRequestTokenUseCase: CreateRequestTokenUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val signOutUseCase: SignOutUseCase,
) : ViewModel() {

    private val _effect = MutableSharedFlow<SettingEffect>()
    val effect = _effect.asSharedFlow()

    private val _state =
        MutableStateFlow<UiState<SettingUiState>>(UiState.Loading)
    val state = _state.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        checkIfUserLoggedIn()
    }

    private fun checkIfUserLoggedIn() {
        launch {
            getUserUseCase()
                .map { user ->
                    SettingUiState(UserProfile(user))
                }
                .asUiState()
                .collect { state ->
                    _state.update { state }
                }
        }
    }

    fun dispatchEvent(event: SettingUiEvent) {
        when (event) {
            SettingUiEvent.SignIn -> {
                createRequestTokenAndNavigateToSignIn()
            }

            is SettingUiEvent.SignOut -> {
                signOut()
            }
        }
    }

    private fun createRequestTokenAndNavigateToSignIn() {
        launch {
            createRequestTokenUseCase()
                .onStart {
                    _loading.update { true }
                }
                .onCompletion {
                    _loading.update { false }
                }
                .catch { _ ->
                    _effect.emit(
                        SettingEffect.ShowSnackbar(
                            state = SnackbarState.SettingToSignInError
                        )
                    )
                }
                .collect { requestToken ->
                    _effect.emit(SettingEffect.SignIn(requestToken = requestToken))
                }
        }
    }

    private fun signOut() {
        launch {
            val uiState = (_state.value as? UiState.Success)?.data ?: return@launch
            val user = uiState.userProfile as UserProfile.User

            signOutUseCase(
                userId = user.id,
                sessionId = user.sessionId
            )
                .onStart {
                    _loading.update { true }
                }
                .onCompletion {
                    _loading.update { false }
                }
                .catch { _ ->
                    _effect.emit(
                        SettingEffect.ShowSnackbar(
                            state = SnackbarState.SettingToSignOutError
                        )
                    )
                }
                .collect { }
        }
    }
}
