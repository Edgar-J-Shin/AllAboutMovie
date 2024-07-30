package com.dcs.presentation.ui.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dcs.domain.model.RequestToken
import com.dcs.domain.usecase.CreateRequestTokenUseCase
import com.dcs.domain.usecase.SignInUseCase
import com.dcs.presentation.BuildConfig
import com.dcs.presentation.core.model.SignInUiState
import com.dcs.presentation.core.state.UiState
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
    createRequestTokenUseCase: CreateRequestTokenUseCase,
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private val _signInState: MutableStateFlow<UiState<SignInUiState>> =
        MutableStateFlow(UiState.Loading)
    val signInState = _signInState.asStateFlow()

    private val _effect = MutableSharedFlow<SignInEffect>()
    val effect = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            createRequestTokenUseCase()
                .catch { throwable ->
                    // Handle failure
                    _signInState.update { UiState.Error(throwable) }
                }
                .collect { requestToken ->
                    _signInState.value =
                        UiState.Success(SignInUiState(BuildConfig.TMDB_AUTH_URL, requestToken))
                }
        }
    }

    fun dispatch(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignIn -> {
                // Handle sign in
                signIn(event.requestToken)
            }
        }
    }

    private fun signIn(requestToken: RequestToken) {
        viewModelScope.launch {
            signInUseCase(requestToken)
                .onStart {
                    val uiState: SignInUiState =
                        (_signInState.value as? UiState.Success)?.data ?: return@onStart
                    _signInState.update { UiState.Success(uiState.copy(loading = true)) }

                }
                .onCompletion {
                    val uiState: SignInUiState =
                        (_signInState.value as? UiState.Success)?.data ?: return@onCompletion
                    _signInState.update {
                        UiState.Success(uiState.copy(loading = false))
                    }
                }
                .catch { throwable ->
                    // Handle failure
                    Log.e(TAG, "signIn: ", throwable)
                    _signInState.update { UiState.Error(throwable) }
                }
                .collect {
                    _effect.emit(SignInEffect.Finish)
                }
        }
    }

    companion object {
        private val TAG = SignInViewModel::class.java.simpleName
    }
}
