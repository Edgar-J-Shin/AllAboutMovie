package com.dcs.data.model.mapper

import com.dcs.data.remote.model.CombinedCredits
import com.dcs.data.remote.model.GetPersonDetailResponse
import com.dcs.data.remote.model.RemoteMediaType
import com.dcs.data.remote.model.RemotePersonCast
import com.dcs.data.remote.model.RemotePersonCrew
import com.dcs.domain.model.KnownFor
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.model.PersonCredit
import com.dcs.domain.model.PersonDetail

fun GetPersonDetailResponse.toEntity(knownFor: List<KnownFor>) = PersonDetail(
    id = id,
    adult = adult,
    alsoKnownAs = alsoKnownAs,
    biography = biography,
    birthday = birthday,
    deathday = deathday,
    gender = gender.toEntity(),
    homepage = homepage,
    imdbId = imdbId,
    knownForDepartment = knownForDepartment,
    name = name,
    placeOfBirth = placeOfBirth,
    popularity = popularity,
    profilePath = profilePath,
    knownFor = knownFor,
    credits = combinedCredits.toEntity()
)

fun CombinedCredits.toEntity(): Map<String, List<PersonCredit>> {
    val map = hashMapOf<String, List<PersonCredit>>()
    if (cast.isNotEmpty()) {
        map[CombinedCredits.KEY_CAST] = cast.map {
            it.toEntity()
        }
    }

    if (crew.isNotEmpty()) {
        crew.forEach {
            val department = it.department

            if (map.containsKey(department)) {
                map[department] = map.getOrDefault(department, listOf()) + it.toEntity()
            } else {
                map[department] = listOf(it.toEntity())
            }
        }
    }

    return map
}

private fun RemotePersonCast.toEntity(): PersonCredit {
    val releaseDate = if (mediaType == RemoteMediaType.MOVIE) {
        releaseDate
    } else {
        firstAirDate
    }
    val title = if (mediaType == RemoteMediaType.MOVIE) {
        title
    } else {
        name
    }

    return PersonCredit(
        id = MediaContentId(id),
        adult = adult,
        mediaType = mediaType.toEntity(),
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        role = character
    )
}

private fun RemotePersonCrew.toEntity(): PersonCredit {
    val releaseDate = if (mediaType == RemoteMediaType.MOVIE) {
        releaseDate
    } else {
        firstAirDate
    }
    val title = if (mediaType == RemoteMediaType.MOVIE) {
        title
    } else {
        name
    }

    return PersonCredit(
        id = MediaContentId(id),
        adult = adult,
        mediaType = mediaType.toEntity(),
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        role = job
    )
}

