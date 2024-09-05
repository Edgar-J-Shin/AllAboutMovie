package com.dcs.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetTvShowDetailResponse(
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    @SerialName("created_by")
    val createdBy: List<RemoteCreatedBy> = emptyList(),
    @SerialName("episode_run_time")
    val episodeRunTime: List<Int> = emptyList(),
    @SerialName("first_air_date")
    val firstAirDate: String = "",
    @SerialName("genres")
    val genres: List<RemoteGenre> = emptyList(),
    @SerialName("homepage")
    val homepage: String = "",
    @SerialName("id")
    val id: Int,
    @SerialName("in_production")
    val inProduction: Boolean = false,
    @SerialName("languages")
    val languages: List<String> = emptyList(),
    @SerialName("last_air_date")
    val lastAirDate: String = "",
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: RemoteEpisodeToAir = RemoteEpisodeToAir(),
    @SerialName("name")
    val name: String = "",
    @SerialName("networks")
    val networks: List<RemoteNetwork> = emptyList(),
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: RemoteEpisodeToAir = RemoteEpisodeToAir(),
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int = 0,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int = 0,
    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerialName("original_language")
    val originalLanguage: String = "",
    @SerialName("original_name")
    val originalName: String = "",
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
    @SerialName("seasons")
    val seasons: List<RemoteSeason> = emptyList(),
    @SerialName("spoken_languages")
    val spokenLanguages: List<RemoteSpokenLanguage> = emptyList(),
    @SerialName("status")
    val status: String = "",
    @SerialName("tagline")
    val tagline: String = "",
    @SerialName("type")
    val type: String = "",
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Int = 0,
    @SerialName("credits")
    val credits: RemoteCredits = RemoteCredits(),
)

@Serializable
data class RemoteCreatedBy(
    @SerialName("credit_id")
    val creditId: String = "",
    @SerialName("gender")
    val gender: Int = 0,
    @SerialName("id")
    val id: Int = -1,
    @SerialName("name")
    val name: String = "",
    @SerialName("original_name")
    val originalName: String = "",
    @SerialName("profile_path")
    val profilePath: String = "",
)

@Serializable
data class RemoteNetwork(
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
data class RemoteEpisodeToAir(
    @SerialName("air_date")
    val airDate: String = "",
    @SerialName("episode_number")
    val episodeNumber: Int = 0,
    @SerialName("episode_type")
    val episodeType: String = "",
    @SerialName("id")
    val id: Int = -1,
    @SerialName("name")
    val name: String = "",
    @SerialName("overview")
    val overview: String = "",
    @SerialName("production_code")
    val productionCode: String = "",
    @SerialName("runtime")
    val runtime: Int = 0,
    @SerialName("season_number")
    val seasonNumber: Int = 0,
    @SerialName("show_id")
    val showId: Int = -1,
    @SerialName("still_path")
    val stillPath: String = "",
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Int = 0
)

@Serializable
data class RemoteSeason(
    @SerialName("air_date")
    val airDate: String = "",
    @SerialName("episode_count")
    val episodeCount: Int = 0,
    @SerialName("id")
    val id: Int = -1,
    @SerialName("name")
    val name: String = "",
    @SerialName("overview")
    val overview: String = "",
    @SerialName("poster_path")
    val posterPath: String = "",
    @SerialName("season_number")
    val seasonNumber: Int = 0,
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
)
