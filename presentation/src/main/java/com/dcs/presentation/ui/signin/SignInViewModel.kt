package com.dcs.presentation.ui.signin

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dcs.domain.model.RequestToken
import com.dcs.domain.usecase.SignInUseCase
import com.dcs.presentation.BuildConfig
import com.dcs.presentation.core.model.SignInUiState
import com.dcs.presentation.core.state.UiState
import com.dcs.presentation.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<SignInUiState>> =
        MutableStateFlow(UiState.Loading)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SignInEffect>()
    val effect = _effect.asSharedFlow()

    private val requestToken: String = savedStateHandle[Screen.REQUEST_TOKEN_KEY]!!

    init {
        _state.update {
            val token = RequestToken(requestToken)
            UiState.Success(
                SignInUiState(
                    baseUrl = BuildConfig.TMDB_AUTH_URL,
                    requestToken = token
                )
            )
        }
    }

    fun dispatch(event: SignInUiEvent) {
        when (event) {
            is SignInUiEvent.SignIn -> {
                // Handle sign in
                signIn(event.requestToken)
            }

            is SignInUiEvent.NavigateBack -> {
                // Handle navigate back
                viewModelScope.launch {
                    _effect.emit(SignInEffect.NavigateBack)
                }
            }
        }
    }

    private fun signIn(requestToken: RequestToken) {
        viewModelScope.launch {
            signInUseCase(requestToken)
                .onStart {
                    val uiState: SignInUiState =
                        (_state.value as? UiState.Success)?.data ?: return@onStart
                    _state.update { UiState.Success(uiState.copy(loading = true)) }

                }
                .onCompletion {
                    val uiState: SignInUiState =
                        (_state.value as? UiState.Success)?.data ?: return@onCompletion
                    _state.update { UiState.Success(uiState.copy(loading = false)) }
                }
                .catch { throwable ->
                    // Handle failure
                    Log.e(TAG, "signIn: ", throwable)
                    _state.update { UiState.Error(throwable) }
                }
                .collect {
                    _effect.emit(SignInEffect.NavigateBack)
                }
        }
    }

    companion object {
        private val TAG = SignInViewModel::class.java.simpleName
    }
}
