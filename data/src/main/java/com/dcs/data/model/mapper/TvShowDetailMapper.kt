package com.dcs.data.model.mapper

import com.dcs.data.remote.model.GetTvShowDetailResponse
import com.dcs.data.remote.model.RemoteCreatedBy
import com.dcs.data.remote.model.RemoteLastEpisodeToAir
import com.dcs.data.remote.model.RemoteNetwork
import com.dcs.data.remote.model.RemoteSeason
import com.dcs.domain.model.CreatedBy
import com.dcs.domain.model.LastEpisodeToAir
import com.dcs.domain.model.Network
import com.dcs.domain.model.Season
import com.dcs.domain.model.TvShowDetail

fun GetTvShowDetailResponse.toEntity() = TvShowDetail(
    adult = adult,
    backdropPath = backdropPath,
    createdBy = createdBy.map { it.toEntity() },
    episodeRunTime = episodeRunTime,
    firstAirDate = firstAirDate,
    genres = genres.map { it.toEntity() },
    homepage = homepage,
    id = id,
    inProduction = inProduction,
    languages = languages,
    lastAirDate = lastAirDate,
    lastEpisodeToAir = lastEpisodeToAir.toEntity(),
    name = name,
    networks = networks.map { it.toEntity() },
    nextEpisodeToAir = nextEpisodeToAir,
    numberOfEpisodes = numberOfEpisodes,
    numberOfSeasons = numberOfSeasons,
    originCountry = originCountry,
    originalLanguage = originalLanguage,
    originalName = originalName,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    productionCompanies = productionCompanies.map { it.toEntity() },
    productionCountries = productionCountries.map { it.toEntity() },
    seasons = seasons.map { it.toEntity() },
    spokenLanguages = spokenLanguages.map { it.toEntity() },
    status = status,
    tagline = tagline,
    type = type,
    voteAverage = voteAverage,
    voteCount = voteCount,
)

fun RemoteCreatedBy.toEntity() = CreatedBy(
    creditId = creditId,
    gender = gender,
    id = id,
    name = name,
    originalName = originalName,
    profilePath = profilePath,
)

fun RemoteLastEpisodeToAir.toEntity() = LastEpisodeToAir(
    airDate = airDate,
    episodeNumber = episodeNumber,
    episodeType = episodeType,
    id = id,
    name = name,
    overview = overview,
    productionCode = productionCode,
    runtime = runtime,
    seasonNumber = seasonNumber,
    showId = showId,
    stillPath = stillPath,
    voteAverage = voteAverage,
    voteCount = voteCount,
)

fun RemoteNetwork.toEntity() = Network(
    id = id,
    logoPath = logoPath,
    name = name,
    originCountry = originCountry,
)

fun RemoteSeason.toEntity() = Season(
    airDate = airDate,
    episodeCount = episodeCount,
    id = id,
    name = name,
    overview = overview,
    posterPath = posterPath,
    seasonNumber = seasonNumber,
    voteAverage = voteAverage,
)
