package com.dcs.domain.usecase

import androidx.paging.PagingData
import com.dcs.domain.model.MediaPolymorphic
import com.dcs.domain.model.MediaType
import com.dcs.domain.model.TimeWindow
import com.dcs.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesByTrendingUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(
        mediaType: MediaType,
        timeWindow: TimeWindow,
    ): Flow<PagingData<MediaPolymorphic>> {
        return movieRepository.getMediaContentsByTrending(
            mediaType = mediaType,
            timeWindow = timeWindow
        )
    }
}
