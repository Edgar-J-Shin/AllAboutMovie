package com.dcs.domain.repository

import androidx.paging.PagingData
import com.dcs.domain.model.MediaPolymorphic
import com.dcs.domain.model.MediaType
import com.dcs.domain.model.MovieDetail
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.model.TimeWindow
import com.dcs.domain.model.TvShowDetail
import kotlinx.coroutines.flow.Flow

interface MediaContentRepository {

    fun getTrendingMediaContents(mediaType: MediaType, timeWindow: TimeWindow): Flow<PagingData<MediaPolymorphic>>

    fun getPopularMovies(): Flow<PagingData<MediaPolymorphic.Movie>>

    fun getTopRatedMovies(): Flow<PagingData<MediaPolymorphic.Movie>>

    fun getUpcomingMovies(): Flow<PagingData<MediaPolymorphic.Movie>>

    fun getSearchContents(query: String): Flow<PagingData<MediaPolymorphic.Movie>>

    fun getMovieById(mediaContentId: MediaContentId): Flow<MovieDetail>

    fun getPopularTvShows(): Flow<PagingData<MediaPolymorphic.TvShow>>

    fun getTvShowById(mediaContentId: MediaContentId): Flow<TvShowDetail>
}
