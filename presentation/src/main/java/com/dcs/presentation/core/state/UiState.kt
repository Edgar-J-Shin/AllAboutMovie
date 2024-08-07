package com.dcs.presentation.core.state

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class UiState<out R> {
    data class Success<out T>(val data: T) : UiState<T>()
    data object Loading : UiState<Nothing>()
    data class Error(val throwable: Throwable) : UiState<Nothing>()
}

fun <T> Flow<T>.asUiState(): Flow<UiState<T>> {
    return this
        .map<T, UiState<T>> { UiState.Success(it) }
        .onStart { emit(UiState.Loading) }
        .catch { emit(UiState.Error(it)) }
}
