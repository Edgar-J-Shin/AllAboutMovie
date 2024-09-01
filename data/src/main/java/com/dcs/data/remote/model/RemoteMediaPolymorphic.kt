package com.dcs.data.remote.model

import com.dcs.domain.model.MediaPolymorphic
import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = RemoteMediaPolymorphic.Serializer::class)
sealed interface RemoteMediaPolymorphic {

    fun toEntity(): MediaPolymorphic

    @Serializable
    data class RemoteMovie(
        @SerialName("adult")
        val adult: Boolean = false,
        @SerialName("backdrop_path")
        val backdropPath: String = "",
        @SerialName("genre_ids")
        val genreIds: List<Int> = emptyList(),
        @SerialName("id")
        val id: Int,
        @SerialName("media_type")
        val mediaType: String = "movie",
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
        @SerialName("release_date")
        val releaseDate: String = "",
        @SerialName("title")
        val title: String = "",
        @SerialName("video")
        val video: Boolean = false,
        @SerialName("vote_average")
        val voteAverage: Double = 0.0,
        @SerialName("vote_count")
        val voteCount: Int = 0,
    ) : RemoteMediaPolymorphic {
        override fun toEntity(): MediaPolymorphic = MediaPolymorphic.Movie(
            adult = adult,
            backdropPath = backdropPath,
            genreIds = genreIds,
            id = id,
            mediaType = mediaType,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount,
        )
    }

    @Serializable
    data class RemoteTvShow(
        @SerialName("backdrop_path")
        val backdropPath: String = "",
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String = "",
        @SerialName("original_name")
        val originalName: String = "",
        @SerialName("overview")
        val overview: String = "",
        @SerialName("poster_path")
        val posterPath: String = "",
        @SerialName("media_type")
        val mediaType: String = "tv",
        @SerialName("adult")
        val adult: Boolean = false,
        @SerialName("original_language")
        val originalLanguage: String = "",
        @SerialName("genre_ids")
        val genreIds: List<Int> = emptyList(),
        @SerialName("popularity")
        val popularity: Double = 0.0,
        @SerialName("first_air_date")
        val firstAirDate: String = "",
        @SerialName("vote_average")
        val voteAverage: Double = 0.0,
        @SerialName("vote_count")
        val voteCount: Int = 0,
        @SerialName("original_country")
        val originalCountry: List<String> = emptyList(),
    ) : RemoteMediaPolymorphic {
        override fun toEntity(): MediaPolymorphic = MediaPolymorphic.TvShow(
            adult = adult,
            backdropPath = backdropPath,
            genreIds = genreIds,
            id = id,
            mediaType = mediaType,
            originalLanguage = originalLanguage,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            voteAverage = voteAverage,
            voteCount = voteCount,
            name = name,
            originalName = originalName,
            firstAirDate = firstAirDate,
            originalCountry = originalCountry
        )
    }

    object Serializer : KSerializer<RemoteMediaPolymorphic> {
        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            prettyPrint = true
            coerceInputValues = true
        }
        override val descriptor: SerialDescriptor
            get() = PolymorphicSerializer(RemoteMediaPolymorphic::class).descriptor

        override fun serialize(encoder: Encoder, value: RemoteMediaPolymorphic) {
            when (value) {
                is RemoteTvShow -> encoder.encodeSerializableValue(RemoteTvShow.serializer(), value)
                is RemoteMovie -> encoder.encodeSerializableValue(RemoteMovie.serializer(), value)
            }
        }

        override fun deserialize(decoder: Decoder): RemoteMediaPolymorphic {
            val jsonElement = (decoder as JsonDecoder).decodeJsonElement()

            return when (val mediaType = jsonElement.jsonObject["media_type"]?.jsonPrimitive?.content) {
                "tv" -> json.decodeFromJsonElement(RemoteTvShow.serializer(), jsonElement)
                "movie" -> json.decodeFromJsonElement(RemoteMovie.serializer(), jsonElement)
                else -> throw SerializationException("Unknown mediaType: $mediaType")
            }
        }
    }
}
