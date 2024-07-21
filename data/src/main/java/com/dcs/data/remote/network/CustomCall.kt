package com.dcs.data.remote.network

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomCall<T : Any>(
    private val callDelegate: Call<T>
) : Call<NetworkResponse<T>> {

    override fun enqueue(callback: Callback<NetworkResponse<T>>) = callDelegate.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val networkResponse = if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResponse.Success(it, response.code())
                } ?: NetworkResponse.Unexpected(Throwable())
            } else {
                NetworkResponse.Failure(response.message(), response.code())
            }

            callback.onResponse(this@CustomCall, Response.success(networkResponse))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            callback.onResponse(this@CustomCall, Response.success(NetworkResponse.Error(t)))
            call.cancel()
        }
    })

    override fun clone(): Call<NetworkResponse<T>> = CustomCall(callDelegate.clone())

    override fun execute(): Response<NetworkResponse<T>> = throw UnsupportedOperationException("ResponseCall does not support execute.")

    override fun isExecuted(): Boolean = callDelegate.isExecuted

    override fun cancel() = callDelegate.cancel()

    override fun isCanceled(): Boolean = callDelegate.isCanceled

    override fun request(): Request = callDelegate.request()

    override fun timeout(): Timeout = callDelegate.timeout()
}