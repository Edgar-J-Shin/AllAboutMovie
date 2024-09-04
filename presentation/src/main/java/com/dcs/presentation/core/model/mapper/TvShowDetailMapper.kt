package com.dcs.presentation.core.model.mapper

import com.dcs.domain.model.CreatedBy
import com.dcs.domain.model.EpisodeToAir
import com.dcs.domain.model.Network
import com.dcs.domain.model.Season
import com.dcs.domain.model.TvShowDetail
import com.dcs.presentation.core.model.CreatedByUiState
import com.dcs.presentation.core.model.EpisodeToAirUiState
import com.dcs.presentation.core.model.NetworkUiState
import com.dcs.presentation.core.model.SeasonUiState
import com.dcs.presentation.core.model.TvShowDetailUiState

fun TvShowDetail.toUiState() = TvShowDetailUiState(
    adult = adult,
    backdropPath = backdropPath,
    createdBy = createdBy.map { it.toUiState() },
    episodeRunTime = episodeRunTime,
    firstAirDate = firstAirDate,
    genres = genres.map { it.toUiState() },
    homepage = homepage,
    id = id,
    inProduction = inProduction,
    languages = languages,
    lastAirDate = lastAirDate,
    lastEpisodeToAir = lastEpisodeToAir.toUiState(),
    name = name,
    networks = networks.map { it.toUiState() },
    nextEpisodeToAir = nextEpisodeToAir.toUiState(),
    numberOfEpisodes = numberOfEpisodes,
    numberOfSeasons = numberOfSeasons,
    originCountry = originCountry,
    originalLanguage = originalLanguage,
    originalName = originalName,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    productionCompanies = productionCompanies.map { it.toUiState() },
    productionCountries = productionCountries.map { it.toUiState() },
    seasons = seasons.map { it.toUiState() },
    spokenLanguages = spokenLanguages.map { it.toUiState() },
    status = status,
    tagline = tagline,
    type = type,
    voteAverage = voteAverage,
    voteCount = voteCount,
    credits = credits.toUiState(),
)

fun CreatedBy.toUiState() = CreatedByUiState(
    creditId = creditId,
    gender = gender,
    id = id,
    name = name,
    originalName = originalName,
    profilePath = profilePath,
)

fun EpisodeToAir.toUiState() = EpisodeToAirUiState(
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

fun Network.toUiState() = NetworkUiState(
    id = id,
    logoPath = logoPath,
    name = name,
    originCountry = originCountry,
)

fun Season.toUiState() = SeasonUiState(
    airDate = airDate,
    episodeCount = episodeCount,
    id = id,
    name = name,
    overview = overview,
    posterPath = posterPath,
    seasonNumber = seasonNumber,
    voteAverage = voteAverage,
)
