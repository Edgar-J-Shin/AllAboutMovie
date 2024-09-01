package com.dcs.data.model

import com.dcs.domain.model.MediaType
import com.dcs.domain.model.TimeWindow

sealed class MediaContentType {
    data class Trending(val mediaType: MediaType, val timeWindow: TimeWindow) : MediaContentType()
}
