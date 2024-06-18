package com.example.domain.usecase

import com.example.domain.model.MovieEntity
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * For Test
 */
class GetMoviesByTopRatedUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Flow<Result<List<MovieEntity>>> {
        return movieRepository.fetchMoviesByTopRated()
    }
}