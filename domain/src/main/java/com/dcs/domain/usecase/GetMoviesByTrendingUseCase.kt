package com.dcs.domain.usecase

import androidx.paging.PagingData
import com.dcs.domain.model.MovieEntity
import com.dcs.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesByTrendingUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(timeWindow: String): Flow<PagingData<MovieEntity>> {
        return movieRepository.getMoviesByTrending(timeWindow = timeWindow)
    }
}