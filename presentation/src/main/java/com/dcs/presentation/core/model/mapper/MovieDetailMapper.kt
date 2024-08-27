package com.dcs.presentation.core.model.mapper


import com.dcs.domain.model.BelongsToCollection
import com.dcs.domain.model.CreditsCast
import com.dcs.domain.model.Credits
import com.dcs.domain.model.CreditsCrew
import com.dcs.domain.model.Genre
import com.dcs.domain.model.MovieDetail
import com.dcs.domain.model.ProductionCompany
import com.dcs.domain.model.ProductionCountry
import com.dcs.domain.model.SpokenLanguage
import com.dcs.presentation.core.model.BelongsToCollectionUiState
import com.dcs.presentation.core.model.CreditsCastUiState
import com.dcs.presentation.core.model.CreditsUiState
import com.dcs.presentation.core.model.CreditsCrewUiState
import com.dcs.presentation.core.model.GenreUiState
import com.dcs.presentation.core.model.MovieDetailUiState
import com.dcs.presentation.core.model.ProductionCompanyUiState
import com.dcs.presentation.core.model.ProductionCountryUiState
import com.dcs.presentation.core.model.SpokenLanguageUiState

fun MovieDetail.toUiState() = MovieDetailUiState(
    adult = adult,
    backdropPath = backdropPath,
    belongsToCollection = belongsToCollection.toUiState(),
    budget = budget,
    credits = credits.toUiState(),
    genres = genres.map { it.toUiState() },
    homepage = homepage,
    id = id,
    imdbId = imdbId,
    originCountry = originCountry,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    productionCompanies = productionCompanies.map { it.toUiState() },
    productionCountries = productionCountries.map { it.toUiState() },
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    spokenLanguages = spokenLanguages.map { it.toUiState() },
    status = status,
    tagline = tagline,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
)

fun BelongsToCollection.toUiState() = BelongsToCollectionUiState(
    backdropPath = backdropPath,
    id = id,
    name = name,
    posterPath = posterPath,
)

fun Credits.toUiState() = CreditsUiState(
    cast = cast.map { it.toUiState() },
    crew = crew.map { it.toUiState() },
)

fun CreditsCast.toUiState() = CreditsCastUiState(
    adult = adult,
    castId = castId,
    character = character,
    creditId = creditId,
    gender = gender,
    id = id,
    knownForDepartment = knownForDepartment,
    name = name,
    order = order,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath,
)

fun CreditsCrew.toUiState() = CreditsCrewUiState(
    adult = adult,
    creditId = creditId,
    department = department,
    gender = gender,
    id = id,
    job = job,
    knownForDepartment = knownForDepartment,
    name = name,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath,
)

fun Genre.toUiState() = GenreUiState(
    id = id,
    name = name,
)

fun ProductionCompany.toUiState() = ProductionCompanyUiState(
    id = id,
    logoPath = logoPath,
    name = name,
    originCountry = originCountry,
)

fun ProductionCountry.toUiState() = ProductionCountryUiState(
    iso31661 = iso31661,
    name = name,
)

fun SpokenLanguage.toUiState() = SpokenLanguageUiState(
    englishName = englishName,
    iso6391 = iso6391,
    name = name,
)
