package com.dcs.data.remote.datasource

import com.dcs.data.remote.model.GetPopularPeopleResponse
import com.dcs.data.remote.network.NetworkResponse

interface PersonRemoteDataSource {

    suspend fun getPopularPeople(
        page: Int,
        language: String,
    ): Result<GetPopularPeopleResponse>
}
