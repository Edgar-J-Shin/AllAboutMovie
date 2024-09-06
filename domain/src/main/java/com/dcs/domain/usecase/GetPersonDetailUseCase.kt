package com.dcs.domain.usecase

import com.dcs.domain.model.Cast
import com.dcs.domain.model.Crew
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

    private fun List<Cast>.sortedByDate(): List<Cast> {
        return withIndex()
            .sortedWith(compareBy<IndexedValue<Cast>> {
                // 1. 둘 다 빈 값이면 인덱스 내림차순으로 정렬
                if (it.value.releaseDate.isBlank() && it.value.firstAirDate.isBlank()) -it.index else 0
            }
                .thenByDescending {
                    // 2. releaseDate가 있으면 releaseDate로 정렬, 없으면 firstAirDate로 정렬
                    val releaseDate = parseDateOrNull(it.value.releaseDate)
                    val firstAirDate = parseDateOrNull(it.value.firstAirDate)
                    releaseDate ?: firstAirDate  // releaseDate가 없으면 firstAirDate를 사용
                }
            )
            .map { it.value }
    }

    private fun List<Crew>.sortByDate(): List<Crew> {
        return withIndex()
            .sortedWith(compareBy<IndexedValue<Crew>> {
                if (it.value.releaseDate.isBlank() && it.value.firstAirDate.isBlank()) -it.index else 0
            }.thenByDescending {
                // 2. releaseDate가 있으면 releaseDate로 정렬, 없으면 firstAirDate로 정렬
                val releaseDate = parseDateOrNull(it.value.releaseDate)
                val firstAirDate = parseDateOrNull(it.value.firstAirDate)
                releaseDate ?: firstAirDate  // releaseDate가 없으면 firstAirDate를 사용
            }.thenByDescending {
                if (it.value.releaseDate.isBlank()) it.index else -1
            })
            .map { it.value }
    }


    operator fun invoke(personId: Long): Flow<PersonDetail> =
        personRepository.getPersonDetail(personId)
            .map {
                // cast, crews를 releaseDate로 정렬,
                // releaseDate, firstAirDate 빈값이 우선순위가 제일 높고 그다음은 최신날짜 순으로 정렬해야함
                it.copy(
                    casts = it.casts.sortedByDate(),
                    crews = it.crews.sortByDate()
                )

            }

}
