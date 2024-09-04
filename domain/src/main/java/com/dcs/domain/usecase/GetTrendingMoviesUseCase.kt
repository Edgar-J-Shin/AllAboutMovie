package com.dcs.domain.usecase

import androidx.paging.PagingData
import com.dcs.domain.model.MediaPolymorphic
import com.dcs.domain.model.MediaType
import com.dcs.domain.model.TimeWindow
import com.dcs.domain.repository.MediaContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val mediaContentRepository: MediaContentRepository,
) {
    operator fun invoke(
        mediaType: MediaType,
        timeWindow: TimeWindow,
    ): Flow<PagingData<MediaPolymorphic>> {
        return mediaContentRepository.getTrendingMediaContents(
            mediaType = mediaType,
            timeWindow = timeWindow
        )
    }
}
