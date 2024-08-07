package com.dcs.domain.repository

import androidx.paging.PagingData
import com.dcs.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    fun getPopularPeople(): Flow<PagingData<Person>>
}
