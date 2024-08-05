package com.dcs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dcs.data.di.IoDispatcher
import com.dcs.data.pagingsource.PopularPeoplePagingSource
import com.dcs.data.remote.datasource.PersonRemoteDataSource
import com.dcs.domain.model.Person
import com.dcs.domain.repository.PersonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val remote: PersonRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : PersonRepository {

    override fun getPopularPeople(): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PopularPeoplePagingSource(
                    remote = remote,
                    pageSize = 20
                )
            }
        ).flow
    }
}
