package com.dcs.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMovieDetailResponse(
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    @SerialName("belongs_to_collection")
    val belongsToCollection: RemoteBelongsToCollection = RemoteBelongsToCollection(),
    @SerialName("budget")
    val budget: Int = 0,
    @SerialName("credits")
    val credits: RemoteCredits = RemoteCredits(),
    @SerialName("genres")
    val genres: List<RemoteGenre> = emptyList(),
    @SerialName("homepage")
    val homepage: String = "",
    @SerialName("id")
    val id: Int = -1,
    @SerialName("imdb_id")
    val imdbId: String = "",
    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerialName("original_language")
    val originalLanguage: String = "",
    @SerialName("original_title")
    val originalTitle: String = "",
    @SerialName("overview")
    val overview: String = "",
    @SerialName("popularity")
    val popularity: Double = 0.0,
    @SerialName("poster_path")
    val posterPath: String = "",
    @SerialName("production_companies")
    val productionCompanies: List<RemoteProductionCompany> = emptyList(),
    @SerialName("production_countries")
    val productionCountries: List<RemoteProductionCountry> = emptyList(),
    @SerialName("release_date")
    val releaseDate: String = "",
    @SerialName("revenue")
    val revenue: Int = 0,
    @SerialName("runtime")
    val runtime: Int = 0,
    @SerialName("spoken_languages")
    val spokenLanguages: List<RemoteSpokenLanguage> = emptyList(),
    @SerialName("status")
    val status: String = "",
    @SerialName("tagline")
    val tagline: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("video")
    val video: Boolean = false,
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Int = 0,
)

@Serializable
data class RemoteBelongsToCollection(
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    @SerialName("id")
    val id: Int = -1,
    @SerialName("name")
    val name: String = "",
    @SerialName("poster_path")
    val posterPath: String = "",
)

@Serializable
data class RemoteCredits(
    @SerialName("cast")
    val cast: List<RemoteCreditsCast> = emptyList(),
    @SerialName("crew")
    val crew: List<RemoteCreditsCrew> = emptyList(),
)

@Serializable
data class RemoteCreditsCast(
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("cast_id")
    val castId: Int = -1,
    @SerialName("character")
    val character: String = "",
    @SerialName("credit_id")
    val creditId: String = "",
    @SerialName("gender")
    val gender: Int = 0,
    @SerialName("id")
    val id: Int = -1,
    @SerialName("known_for_department")
    val knownForDepartment: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("order")
    val order: Int = 0,
    @SerialName("original_name")
    val originalName: String = "",
    @SerialName("popularity")
    val popularity: Double = 0.0,
    @SerialName("profile_path")
    val profilePath: String = "",
)

@Serializable
data class RemoteCreditsCrew(
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("credit_id")
    val creditId: String = "",
    @SerialName("department")
    val department: String = "",
    @SerialName("gender")
    val gender: Int = 0,
    @SerialName("id")
    val id: Int = -1,
    @SerialName("job")
    val job: String = "",
    @SerialName("known_for_department")
    val knownForDepartment: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("original_name")
    val originalName: String = "",
    @SerialName("popularity")
    val popularity: Double = 0.0,
    @SerialName("profile_path")
    val profilePath: String = "",
)

@Serializable
data class RemoteGenre(
    @SerialName("id")
    val id: Int = -1,
    @SerialName("name")
    val name: String = "",
)

@Serializable
data class RemoteProductionCompany(
    @SerialName("id")
    val id: Int = -1,
    @SerialName("logo_path")
    val logoPath: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("origin_country")
    val originCountry: String = "",
)

@Serializable
data class RemoteProductionCountry(
    @SerialName("iso_3166_1")
    val iso31661: String = "",
    @SerialName("name")
    val name: String = "",
)

@Serializable
data class RemoteSpokenLanguage(
    @SerialName("english_name")
    val englishName: String = "",
    @SerialName("iso_639_1")
    val iso6391: String = "",
    @SerialName("name")
    val name: String = "",
)
