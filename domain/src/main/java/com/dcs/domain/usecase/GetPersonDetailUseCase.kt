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
        return withIndex()  // 인덱스를 보관하기 위해 withIndex 사용
            .sortedWith(compareBy<IndexedValue<Cast>> {
                // 1. releaseDate 또는 firstAirDate가 하나라도 있으면 우선순위를 낮게 설정
                it.value.releaseDate.isNotBlank() || it.value.firstAirDate.isNotBlank()
            }.thenByDescending {
                // 2. releaseDate가 있으면 최신 날짜 순으로 정렬
                parseDateOrNull(it.value.releaseDate)
            }.thenByDescending {
                // 3. releaseDate가 없으면 firstAirDate 기준으로 최신 날짜 순으로 정렬
                parseDateOrNull(it.value.firstAirDate)
            }.thenByDescending {
                // 4. releaseDate와 firstAirDate가 모두 빈 값인 경우 인덱스 내림차순으로 정렬
                if (it.value.releaseDate.isBlank() && it.value.firstAirDate.isBlank()) it.index else -1
            })
            .map { it.value }  // 다시 원래 리스트 형태로 변환
    }

    private fun List<Crew>.sortByReleaseDate(): List<Crew> {
        return withIndex()
            .sortedWith(compareBy<IndexedValue<Crew>> {
                it.value.releaseDate.isNotBlank()
            }.thenByDescending {
                parseDateOrNull(it.value.releaseDate)
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
                    crews = it.crews.sortByReleaseDate()
                )

            }

}
