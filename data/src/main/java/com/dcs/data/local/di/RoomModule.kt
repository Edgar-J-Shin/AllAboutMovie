package com.dcs.data.local.di

import android.content.Context
import androidx.room.Room
import com.dcs.data.local.room.AllAboutMovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AllAboutMovieDatabase {
        return Room.databaseBuilder(
            context,
            AllAboutMovieDatabase::class.java,
            AllAboutMovieDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigrationFrom()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserEntityDao(allAboutMovieDatabase: AllAboutMovieDatabase) =
        allAboutMovieDatabase.userDao()
}
