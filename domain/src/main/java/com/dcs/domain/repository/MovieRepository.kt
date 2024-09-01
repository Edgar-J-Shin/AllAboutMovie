package com.dcs.domain.repository

import androidx.paging.PagingData
import com.dcs.domain.model.MediaPolymorphic
import com.dcs.domain.model.MediaType
import com.dcs.domain.model.MovieDetail
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.model.TimeWindow
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMediaContentsByTrending(mediaType: MediaType, timeWindow: TimeWindow): Flow<PagingData<MediaPolymorphic>>

    fun getMoviesByPopular(): Flow<PagingData<MediaPolymorphic.Movie>>

    fun getMoviesByTopRated(): Flow<PagingData<MediaPolymorphic.Movie>>

    fun getMoviesByUpcoming(): Flow<PagingData<MediaPolymorphic.Movie>>

    fun getSearchContents(query: String): Flow<PagingData<MediaPolymorphic.Movie>>

    fun getMovieById(mediaContentId: MediaContentId): Flow<MovieDetail>

    fun getPopularTvShows(): Flow<PagingData<MediaPolymorphic.TvShow>>
}
