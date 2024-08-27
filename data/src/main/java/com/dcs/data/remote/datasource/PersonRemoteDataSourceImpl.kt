package com.dcs.data.remote.datasource

import com.dcs.data.remote.model.GetPersonDetailResponse
import com.dcs.data.remote.model.GetPopularPeopleResponse
import com.dcs.data.remote.model.GetSearchPersonResponse
import com.dcs.data.remote.service.PersonService
import com.dcs.data.remote.service.SearchService
import javax.inject.Inject

class PersonRemoteDataSourceImpl @Inject constructor(
    private val personService: PersonService,
    private val searchService: SearchService,
) : PersonRemoteDataSource {
    override suspend fun getPopularPeople(
        page: Int,
        language: String,
    ): Result<GetPopularPeopleResponse> {
        return personService.getPopularPeople(
            page = page,
            language = language
        ).asResult {
            it.data as GetPopularPeopleResponse
        }
    }

    override suspend fun getPersonDetail(personId: Long): Result<GetPersonDetailResponse> {
        return personService.getPersonDetail(
            personId = personId
        ).asResult {
            it.data as GetPersonDetailResponse
        }
    }

    override suspend fun getSearchPerson(personName: String): Result<GetSearchPersonResponse> {
        return searchService.getSearchPerson(
            query = personName
        ).asResult {
            it.data as GetSearchPersonResponse
        }
    }

}
