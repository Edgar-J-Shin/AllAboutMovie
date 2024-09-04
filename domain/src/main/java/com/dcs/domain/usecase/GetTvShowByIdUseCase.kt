package com.dcs.domain.usecase

import com.dcs.domain.model.MediaContentId
import com.dcs.domain.model.TvShowDetail
import com.dcs.domain.repository.MediaContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTvShowByIdUseCase @Inject constructor(
    private val mediaContentRepository: MediaContentRepository,
) {
    operator fun invoke(mediaContentId: MediaContentId): Flow<TvShowDetail> {
        return mediaContentRepository.getTvShowById(
            mediaContentId = mediaContentId
        )
    }
}
