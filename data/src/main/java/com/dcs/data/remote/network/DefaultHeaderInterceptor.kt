package com.dcs.data.remote.network

import com.dcs.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class DefaultHeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        val url = chain.request().url
        val urlBuilder = url.newBuilder()
        if (url.queryParameter("api_key") == null) {
            urlBuilder.addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
        }

        getDefaultHeader().forEach { (key, value) ->
            builder.addHeader(key, value)
        }

        return chain.proceed(
            builder
                .url(urlBuilder.build())
                .build()
        )
    }

    private fun getDefaultHeader(): Map<String, String> = mapOf()
}