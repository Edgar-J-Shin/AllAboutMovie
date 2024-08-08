package com.dcs.data.local.di

import com.dcs.data.local.datasource.AuthLocalDataSource
import com.dcs.data.local.datasource.AuthLocalDataSourceImpl
import com.dcs.data.local.datasource.KeywordLocalDataSource
import com.dcs.data.local.datasource.KeywordLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindAuthLocalDataSource(authLocalDataSourceImpl: AuthLocalDataSourceImpl): AuthLocalDataSource

    @Binds
    @Singleton
    abstract fun bindKeywordLocalDataSource(keywordLocalDataSourceImpl: KeywordLocalDataSourceImpl): KeywordLocalDataSource
}
