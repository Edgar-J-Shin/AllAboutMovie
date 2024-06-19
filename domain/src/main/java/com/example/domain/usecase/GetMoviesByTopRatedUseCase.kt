package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.MovieEntity
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesByTopRatedUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Flow<PagingData<MovieEntity>> {
        return movieRepository.getMoviesByTopRated()
    }
}