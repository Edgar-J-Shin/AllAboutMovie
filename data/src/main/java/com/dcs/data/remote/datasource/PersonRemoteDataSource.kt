package com.dcs.data.remote.datasource

import com.dcs.data.remote.model.GetPersonDetailResponse
import com.dcs.data.remote.model.GetPopularPeopleResponse
import com.dcs.data.remote.model.GetSearchPersonResponse

interface PersonRemoteDataSource {

    suspend fun getPopularPeople(
        page: Int,
        language: String,
    ): Result<GetPopularPeopleResponse>

    suspend fun getPersonDetail(personId: Int): Result<GetPersonDetailResponse>

    suspend fun getSearchPerson(personName: String): Result<GetSearchPersonResponse>
}
