package com.dcs.data.remote.network

sealed class NetworkResponse<out T : Any> {
    // status code == 200 ~ 299
    data class Success<T : Any>(val data: T, val code: Int) : NetworkResponse<T>()

    // status code != 200 ~ 299
    data class Failure(val message: String, val code: Int) : NetworkResponse<Nothing>()

    // Network Error
    data class Error(val throwable: Throwable) : NetworkResponse<Nothing>()

    // Others..
    data class Unexpected(val throwable: Throwable) : NetworkResponse<Nothing>()

    inline fun <T> handleNetworkResponse(onSuccessAction: (Success<*>) -> T): Result<T> = when (this) {
        is Success -> Result.success(onSuccessAction(this))
        is Failure -> Result.failure(RetrofitFailureException(message, code))
        is Error -> Result.failure(IllegalStateException("Error : ${throwable.message}"))
        is Unexpected -> Result.failure(IllegalStateException("UnexpectedError : ${throwable.message}"))
    }
}
