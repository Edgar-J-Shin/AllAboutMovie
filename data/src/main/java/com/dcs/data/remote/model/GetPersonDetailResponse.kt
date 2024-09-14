package com.dcs.data.remote.model

import com.dcs.data.remote.network.serializer.GenderSerializer
import com.dcs.data.remote.network.serializer.MediaTypeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPersonDetailResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("also_known_as")
    val alsoKnownAs: List<String>,
    @SerialName("biography")
    val biography: String,
    @SerialName("birthday")
    val birthday: String = "",
    @SerialName("combined_credits")
    val combinedCredits: CombinedCredits,
    @SerialName("deathday")
    val deathday: String = "",
    @SerialName("gender")
    @Serializable(with = GenderSerializer::class)
    val gender: RemoteGender,
    @SerialName("homepage")
    val homepage: String = "",
    @SerialName("imdb_id")
    val imdbId: String = "",
    @SerialName("known_for_department")
    val knownForDepartment: String,
    @SerialName("name")
    val name: String = "",
    @SerialName("place_of_birth")
    val placeOfBirth: String = "",
    @SerialName("popularity")
    val popularity: Double = 0.0,
    @SerialName("profile_path")
    val profilePath: String = "",
    @SerialName("images")
    val profileImages: ProfileImages,
)

@Serializable
data class CombinedCredits(
    @SerialName("cast")
    val cast: List<RemotePersonCast>,
    @SerialName("crew")
    val crew: List<RemotePersonCrew>,
) {
    companion object {
        const val KEY_CAST = "Acting"
    }
}

@Serializable
data class RemotePersonCast(
    @SerialName("id")
    val id: Int,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    @SerialName("character")
    val character: String,
    @SerialName("credit_id")
    val creditId: String,
    @SerialName("episode_count")
    val episodeCount: Int = 0,
    @SerialName("first_air_date")
    val firstAirDate: String = "",
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("media_type")
    @Serializable(with = MediaTypeSerializer::class)
    val mediaType: RemoteMediaType,
    @SerialName("name")
    val name: String = "",
    @SerialName("order")
    val order: Int = 0,
    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String = "",
    @SerialName("original_title")
    val originalTitle: String = "",
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String = "",
    @SerialName("release_date")
    val releaseDate: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("video")
    val video: Boolean = false,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
)

@Serializable
data class RemotePersonCrew(
    @SerialName("id")
    val id: Int,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    @SerialName("credit_id")
    val creditId: String,
    @SerialName("department")
    val department: String,
    @SerialName("episode_count")
    val episodeCount: Int = 0,
    @SerialName("first_air_date")
    val firstAirDate: String = "",
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("job")
    val job: String,
    @SerialName("media_type")
    @Serializable(with = MediaTypeSerializer::class)
    val mediaType: RemoteMediaType,
    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String = "",
    @SerialName("original_name")
    val originalName: String = "",
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String = "",
    @SerialName("release_date")
    val releaseDate: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("video")
    val video: Boolean = false,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("name")
    val name: String = "",
)
