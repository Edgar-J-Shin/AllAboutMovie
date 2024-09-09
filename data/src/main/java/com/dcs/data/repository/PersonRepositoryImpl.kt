package com.dcs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dcs.data.di.IoDispatcher
import com.dcs.data.model.mapper.toEntity
import com.dcs.data.pagingsource.PopularPeoplePagingSource
import com.dcs.data.remote.datasource.PersonRemoteDataSource
import com.dcs.domain.model.KnownFor
import com.dcs.domain.model.Person
import com.dcs.domain.model.PersonDetail
import com.dcs.domain.repository.PersonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val remote: PersonRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : PersonRepository {

    override fun getPopularPeople(): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PopularPeoplePagingSource(
                    remote = remote,
                )
            }
        ).flow
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPersonDetail(personId: Int): Flow<PersonDetail> =
        flow {
            emit(remote.getPersonDetail(personId).getOrThrow())
        }
            .flatMapLatest { personDetailResponse ->
                flow {
                    emit(remote.getSearchPerson(personDetailResponse.name).getOrThrow())
                }
                    .map { searchPersonResponse ->
                        val knownFor: List<KnownFor> =
                            searchPersonResponse.results
                                .flatMap { it.knownFor }
                                .map { it.toEntity() }
                        personDetailResponse.toEntity(knownFor)
                    }
            }
            .flowOn(ioDispatcher)

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}
