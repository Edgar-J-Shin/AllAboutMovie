package com.dcs.presentation.core.model.mapper

import com.dcs.domain.model.KeywordEntity
import com.dcs.presentation.core.model.KeywordUiState

fun KeywordEntity.toUiState() = KeywordUiState(
    keyword = keyword

)
