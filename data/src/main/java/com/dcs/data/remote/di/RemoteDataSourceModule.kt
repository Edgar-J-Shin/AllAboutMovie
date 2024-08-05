package com.dcs.data.remote.di

import com.dcs.data.remote.datasource.AuthRemoteDataSource
import com.dcs.data.remote.datasource.AuthRemoteDataSourceImpl
import com.dcs.data.remote.datasource.MovieRemoteDataSource
import com.dcs.data.remote.datasource.MovieRemoteDataSourceImpl
import com.dcs.data.remote.datasource.PersonRemoteDataSource
import com.dcs.data.remote.datasource.PersonRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteDataSourceModule() {
    @Binds
    @Singleton
    abstract fun bindMovieRemoteDataSource(movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindPersonRemoteDataSource(personRemoteDataSourceImpl: PersonRemoteDataSourceImpl): PersonRemoteDataSource
}
