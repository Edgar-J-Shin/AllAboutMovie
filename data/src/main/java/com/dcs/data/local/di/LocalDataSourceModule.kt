package com.dcs.data.local.di

import com.dcs.data.local.datastore.AuthLocalDataSource
import com.dcs.data.local.datastore.AuthLocalDataSourceImpl
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
}
