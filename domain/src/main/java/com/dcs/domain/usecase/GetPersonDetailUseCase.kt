package com.dcs.domain.usecase

import com.dcs.domain.model.PersonDetail
import com.dcs.domain.repository.PersonRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class GetPersonDetailUseCase @Inject constructor(
    private val personRepository: PersonRepository,
) {
    operator fun invoke(personId: Long): Flow<PersonDetail> =
        personRepository.getPersonDetail(personId)
}
