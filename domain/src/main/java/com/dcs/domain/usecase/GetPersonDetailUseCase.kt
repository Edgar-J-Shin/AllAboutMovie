package com.dcs.domain.usecase

import com.dcs.domain.model.PersonCredit
import com.dcs.domain.model.PersonDetail
import com.dcs.domain.repository.PersonRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

@Reusable
class GetPersonDetailUseCase @Inject constructor(
    private val personRepository: PersonRepository,
) {
    private fun parseDateOrNull(date: String): LocalDate? {
        return if (date.isBlank()) null else LocalDate.parse(date)
    }

    private fun List<PersonCredit>.sortByDate(): List<PersonCredit> {
        return withIndex()
            .sortedWith(compareBy<IndexedValue<PersonCredit>> {
                if (it.value.releaseDate.isBlank()) -it.index else 0
            }.thenByDescending {
                // 2. releaseDate가 있으면 releaseDate로 정렬, 없으면 firstAirDate로 정렬
                parseDateOrNull(it.value.releaseDate)
            }.thenByDescending {
                if (it.value.releaseDate.isBlank()) it.index else -1
            })
            .map { it.value }
    }

    operator fun invoke(personId: Int): Flow<PersonDetail> =
        personRepository.getPersonDetail(personId)
            .map {
                it.copy(
                    credits = it.credits.mapValues { item ->
                        item.value.sortByDate()
                    }
                )
            }

}
