package com.dcs.data.local.room.entity.mapper

import com.dcs.data.local.room.entity.Keyword
import com.dcs.domain.model.KeywordEntity

fun Keyword.toEntity() = KeywordEntity(
    keyword = keyword
)

fun KeywordEntity.toLocalData() = Keyword(
    keyword = keyword
)
