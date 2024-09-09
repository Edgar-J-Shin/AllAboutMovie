package com.dcs.data.remote.service

import com.dcs.data.remote.model.GetPersonDetailResponse
import com.dcs.data.remote.model.GetPopularPeopleResponse
import com.dcs.data.remote.network.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonService {

    @GET("person/popular")
    suspend fun getPopularPeople(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): NetworkResponse<GetPopularPeopleResponse>

    @GET("person/{person_id}")
    suspend fun getPersonDetail(
        @Path("person_id") personId: Int,
        @Query("language") language: String = "en-US",
        @Query("append_to_response") appendToResponse: String = "combined_credits,images",
    ): NetworkResponse<GetPersonDetailResponse>
}
