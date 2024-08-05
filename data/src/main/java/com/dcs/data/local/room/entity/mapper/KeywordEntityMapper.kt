package com.dcs.data.local.room.entity.mapper

import com.dcs.data.local.room.entity.Keyword

fun Keyword.toEntity() = com.dcs.domain.model.Keyword(
    id = id,
    keyword = keyword
)

fun com.dcs.domain.model.Keyword.toLocalData() = Keyword(
    id = id,
    keyword = keyword
)
