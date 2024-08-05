package com.dcs.data.remote.datasource

import com.dcs.data.remote.model.GetPopularPeopleResponse
import com.dcs.data.remote.service.PersonApiService
import javax.inject.Inject

class PersonRemoteDataSourceImpl @Inject constructor(
    private val api: PersonApiService,
) : PersonRemoteDataSource {
    override fun getPopularPeople(
        page: Int,
        language: String,
    ): Result<GetPopularPeopleResponse> {
        return api.getPopularPeople(
            page = page,
            language = language
        ).asResult {
            it.data as GetPopularPeopleResponse
        }
    }
}
