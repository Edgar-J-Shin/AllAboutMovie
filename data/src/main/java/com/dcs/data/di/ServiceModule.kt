package com.dcs.data.di

import com.dcs.data.remote.service.AuthApiService
import com.dcs.data.remote.service.MovieService
import com.dcs.data.remote.service.PersonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Provides
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Provides
    fun providePersonApiService(retrofit: Retrofit): PersonApiService =
        retrofit.create(PersonApiService::class.java)
}
