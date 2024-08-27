package com.dcs.data.model.mapper

import com.dcs.data.remote.model.GetMovieDetailResponse
import com.dcs.data.remote.model.RemoteBelongsToCollection
import com.dcs.data.remote.model.RemoteCredits
import com.dcs.data.remote.model.RemoteCreditsCast
import com.dcs.data.remote.model.RemoteCreditsCrew
import com.dcs.data.remote.model.RemoteGenre
import com.dcs.data.remote.model.RemoteProductionCompany
import com.dcs.data.remote.model.RemoteProductionCountry
import com.dcs.data.remote.model.RemoteSpokenLanguage
import com.dcs.domain.model.BelongsToCollection
import com.dcs.domain.model.Credits
import com.dcs.domain.model.CreditsCast
import com.dcs.domain.model.CreditsCrew
import com.dcs.domain.model.Genre
import com.dcs.domain.model.MovieDetail
import com.dcs.domain.model.ProductionCompany
import com.dcs.domain.model.ProductionCountry
import com.dcs.domain.model.SpokenLanguage

fun GetMovieDetailResponse.toEntity() = MovieDetail(
    adult = adult,
    backdropPath = backdropPath,
    belongsToCollection = belongsToCollection.toEntity(),
    budget = budget,
    credits = credits.toEntity(),
    genres = genres.map { it.toEntity() },
    homepage = homepage,
    id = id,
    imdbId = imdbId,
    originCountry = originCountry,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    productionCompanies = productionCompanies.map { it.toEntity() },
    productionCountries = productionCountries.map { it.toEntity() },
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    spokenLanguages = spokenLanguages.map { it.toEntity() },
    status = status,
    tagline = tagline,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
)

fun RemoteBelongsToCollection.toEntity() = BelongsToCollection(
    backdropPath = backdropPath,
    id = id,
    name = name,
    posterPath = posterPath,
)

fun RemoteCredits.toEntity() = Credits(
    cast = cast.map { it.toEntity() },
    crew = crew.map { it.toEntity() },
)

fun RemoteCreditsCast.toEntity() = CreditsCast(
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

fun RemoteCreditsCrew.toEntity() = CreditsCrew(
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

fun RemoteGenre.toEntity() = Genre(
    id = id,
    name = name,
)

fun RemoteProductionCompany.toEntity() = ProductionCompany(
    id = id,
    logoPath = logoPath,
    name = name,
    originCountry = originCountry,
)

fun RemoteProductionCountry.toEntity() = ProductionCountry(
    iso31661 = iso31661,
    name = name,
)

fun RemoteSpokenLanguage.toEntity() = SpokenLanguage(
    englishName = englishName,
    iso6391 = iso6391,
    name = name,
)
