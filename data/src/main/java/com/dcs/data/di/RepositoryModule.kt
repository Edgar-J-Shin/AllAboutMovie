package com.dcs.data.di

import com.dcs.domain.repository.MovieRepository
import com.dcs.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindBookRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}