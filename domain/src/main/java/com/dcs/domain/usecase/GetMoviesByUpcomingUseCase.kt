package com.dcs.domain.usecase

import androidx.paging.PagingData
import com.dcs.domain.model.Movie
import com.dcs.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesByUpcomingUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return movieRepository.getMoviesByUpcoming()
    }
}
