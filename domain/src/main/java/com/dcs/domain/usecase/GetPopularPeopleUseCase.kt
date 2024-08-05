package com.dcs.domain.usecase

import androidx.paging.PagingData
import com.dcs.domain.model.Person
import com.dcs.domain.repository.PersonRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class GetPopularPeopleUseCase @Inject constructor(
    private val repo: PersonRepository,
) {

    operator fun invoke(): Flow<PagingData<Person>> {
        return repo.getPopularPeople()
    }
}
