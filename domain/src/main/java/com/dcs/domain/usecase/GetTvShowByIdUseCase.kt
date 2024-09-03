package com.dcs.domain.usecase

import com.dcs.domain.model.MediaContentId
import com.dcs.domain.model.TvShowDetail
import com.dcs.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTvShowByIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(mediaContentId: MediaContentId): Flow<TvShowDetail> {
        return movieRepository.getTvShowById(
            mediaContentId = mediaContentId
        )
    }
}
