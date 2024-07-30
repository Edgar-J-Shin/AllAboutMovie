package com.dcs.data.model.mapper

import com.dcs.data.remote.model.CreateSessionIdResponse
import com.dcs.domain.model.SessionId

fun CreateSessionIdResponse.toEntity() = SessionId(this.sessionId)
