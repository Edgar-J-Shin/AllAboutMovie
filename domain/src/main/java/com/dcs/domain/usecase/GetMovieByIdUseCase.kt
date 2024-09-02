package com.dcs.domain.usecase

import com.dcs.domain.model.MovieDetail
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(mediaContentId: MediaContentId): Flow<MovieDetail> {
        return movieRepository.getMovieById(
            mediaContentId = mediaContentId
        )
    }
}
