package com.dcs.data.remote.network

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class CustomCallAdapter<T : Any>(
    private val successType: Type,
    private val retrofit: Retrofit,
) : CallAdapter<T, Call<NetworkResponse<T>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<NetworkResponse<T>> {
        return CustomCall(call, retrofit)
    }
}
