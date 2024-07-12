package com.dcs.presentation.core.state

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

sealed class UiState<out R> {
    data class Success<out T>(val data: T?) : UiState<T>()
    data class Failure(val throwable: Throwable) : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Error(val throwable: Throwable) : UiState<Nothing>()
}

private const val RETRY_ATTEMPT_COUNT = 3
private const val RETRY_TIME_IN_MILLIS = 1000L
fun <T> Flow<T>.asUiState(): Flow<UiState<StateFlow<T>>> {
    return this
        .map<T, UiState<StateFlow<T>>> { UiState.Success(MutableStateFlow(it)) }
        .onStart { emit(UiState.Loading) }
        .retryWhen { cause, attempt ->
            if (cause is IOException && attempt < RETRY_ATTEMPT_COUNT) {
                delay(RETRY_TIME_IN_MILLIS)
                true
            } else {
                false
            }
        }
        .catch { emit(UiState.Error(it)) }
}