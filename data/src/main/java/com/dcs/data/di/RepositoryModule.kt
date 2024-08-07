package com.dcs.data.di

import com.dcs.data.repository.AuthRepositoryImpl
import com.dcs.data.repository.MovieRepositoryImpl
import com.dcs.data.repository.PersonRepositoryImpl
import com.dcs.domain.repository.AuthRepository
import com.dcs.domain.repository.MovieRepository
import com.dcs.domain.repository.PersonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindBookRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindPersonRepository(personRepositoryImpl: PersonRepositoryImpl): PersonRepository
}
