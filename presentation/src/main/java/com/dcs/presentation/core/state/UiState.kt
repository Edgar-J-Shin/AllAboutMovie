package com.dcs.presentation.core.state

sealed class UiState<out R> {
    data class Success<out T>(val data: T?) : UiState<T>()
    data class Failure(val throwable: Throwable) : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Error(val throwable: Throwable) : UiState<Nothing>()
    data object Empty : UiState<Nothing>()
}