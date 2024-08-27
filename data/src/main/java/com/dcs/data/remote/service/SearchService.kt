package com.dcs.data.remote.service

import com.dcs.data.remote.model.GetSearchPersonResponse
import com.dcs.data.remote.network.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/person")
    suspend fun getSearchPerson(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
        @Query("include_adult") includeAdult: Boolean = false,
    ): NetworkResponse<GetSearchPersonResponse>
}
