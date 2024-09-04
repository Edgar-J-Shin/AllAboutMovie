package com.dcs.data.di

import com.dcs.data.remote.service.AuthService
import com.dcs.data.remote.service.MediaContentService
import com.dcs.data.remote.service.PersonService
import com.dcs.data.remote.service.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideMediaContentService(retrofit: Retrofit): MediaContentService =
        retrofit.create(MediaContentService::class.java)

    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    fun providePersonService(retrofit: Retrofit): PersonService =
        retrofit.create(PersonService::class.java)

    @Provides
    fun provideSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)
}
