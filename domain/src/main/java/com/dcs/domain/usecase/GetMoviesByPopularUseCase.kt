package com.dcs.domain.usecase

import androidx.paging.PagingData
import com.dcs.domain.model.MovieEntity
import com.dcs.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesByPopularUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(mediaType: String): Flow<PagingData<MovieEntity>> {
        return movieRepository.getMoviesByPopular(mediaType = mediaType)
    }
}