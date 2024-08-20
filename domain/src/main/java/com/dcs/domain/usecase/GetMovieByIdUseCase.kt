package com.dcs.domain.usecase

import com.dcs.domain.model.Movie
import com.dcs.domain.model.MovieId
import com.dcs.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(movieId: MovieId): Flow<Movie> {
        return movieRepository.getMovieById(
            movieId = movieId
        )
    }
}
