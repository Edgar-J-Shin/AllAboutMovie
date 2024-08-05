package com.dcs.data.remote.service

import com.dcs.data.remote.model.GetPopularPeopleResponse
import com.dcs.data.remote.network.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonApiService {

    @GET("person/popular")
    fun getPopularPeople(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): NetworkResponse<GetPopularPeopleResponse>
}
