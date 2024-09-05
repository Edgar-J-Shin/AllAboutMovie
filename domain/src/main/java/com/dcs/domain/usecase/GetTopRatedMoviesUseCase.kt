package com.dcs.domain.usecase

import androidx.paging.PagingData
import com.dcs.domain.model.MediaPolymorphic
import com.dcs.domain.repository.MediaContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val mediaContentRepository: MediaContentRepository
) {
    operator fun invoke(): Flow<PagingData<MediaPolymorphic.Movie>> {
        return mediaContentRepository.getTopRatedMovies()
    }
}
