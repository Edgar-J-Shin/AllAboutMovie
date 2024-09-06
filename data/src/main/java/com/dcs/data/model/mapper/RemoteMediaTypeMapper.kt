package com.dcs.data.model.mapper

import com.dcs.data.remote.model.RemoteMediaType
import com.dcs.domain.model.MediaType

fun RemoteMediaType.toEntity() = when (this) {
    RemoteMediaType.MOVIE -> MediaType.MOVIE
    RemoteMediaType.TV_SHOW -> MediaType.TV_SHOW
}
