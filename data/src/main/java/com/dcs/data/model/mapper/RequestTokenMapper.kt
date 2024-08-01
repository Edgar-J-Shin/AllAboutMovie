package com.dcs.data.model.mapper

import com.dcs.data.remote.model.CreateRequestTokenResponse
import com.dcs.domain.model.RequestToken

fun CreateRequestTokenResponse.toEntity() = RequestToken(this.requestToken)
