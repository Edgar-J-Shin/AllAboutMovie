package com.dcs.data.di

import com.dcs.data.BuildConfig
import com.dcs.data.remote.network.DefaultHeaderInterceptor
import com.dcs.data.remote.network.Network2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideJsonConverterFactory(): Converter.Factory {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            prettyPrint = true
            isLenient = true
        }

        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(defaultHeaderInterceptor: DefaultHeaderInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(defaultHeaderInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, jsonConverterFactory: Converter.Factory): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_API_URL)
            .addConverterFactory(jsonConverterFactory)
            .addCallAdapterFactory(Network2CallAdapterFactory())
            .client(okHttpClient)
            .build()
}
