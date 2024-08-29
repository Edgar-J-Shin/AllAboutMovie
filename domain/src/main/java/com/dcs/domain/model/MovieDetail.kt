package com.dcs.domain.model

data class MovieDetail(
    val adult: Boolean,
    val backdropPath: String,
    val belongsToCollection: BelongsToCollection,
    val budget: Int,
    val credits: Credits,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdbId: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
)

data class BelongsToCollection(
    val backdropPath: String,
    val id: Int,
    val name: String,
    val posterPath: String,
)

data class Credits(
    val cast: List<CreditsCast>,
    val crew: List<CreditsCrew>,
)

data class CreditsCast(
    val adult: Boolean,
    val castId: Int,
    val character: String,
    val creditId: String,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    val originalName: String,
    val popularity: Double,
    val profilePath: String,
)

data class CreditsCrew(
    val adult: Boolean,
    val creditId: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String,
)

data class Genre(
    val id: Int,
    val name: String,
)

data class ProductionCompany(
    val id: Int,
    val logoPath: String,
    val name: String,
    val originCountry: String,
)

data class ProductionCountry(
    val iso31661: String,
    val name: String,
)

data class SpokenLanguage(
    val englishName: String,
    val iso6391: String,
    val name: String,
)
