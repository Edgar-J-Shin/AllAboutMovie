package com.dcs.domain.usecase

import com.dcs.domain.model.MovieDetail
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.repository.MediaContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val mediaContentRepository: MediaContentRepository,
) {
    operator fun invoke(mediaContentId: MediaContentId): Flow<MovieDetail> {
        return mediaContentRepository.getMovieById(
            mediaContentId = mediaContentId
        )
    }
}
