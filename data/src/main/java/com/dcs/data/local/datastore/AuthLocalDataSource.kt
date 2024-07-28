package com.dcs.data.local.datastore

import com.dcs.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {

    fun getUser(): Flow<User?>
}
