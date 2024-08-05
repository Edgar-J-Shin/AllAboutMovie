package com.dcs.data.remote.datasource

import com.dcs.data.remote.model.GetPopularPeopleResponse
import com.dcs.data.remote.network.NetworkResponse

interface PersonRemoteDataSource {

    fun getPopularPeople(
        page: Int,
        language: String,
    ): NetworkResponse<GetPopularPeopleResponse>
}
