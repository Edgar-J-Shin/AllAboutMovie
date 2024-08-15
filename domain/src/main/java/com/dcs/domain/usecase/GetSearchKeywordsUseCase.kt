package com.dcs.domain.usecase

import com.dcs.domain.model.Keyword
import com.dcs.domain.repository.KeywordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSearchKeywordsUseCase @Inject constructor(
    private val keywordsRepository: KeywordsRepository,
) {
    operator fun invoke(): Flow<List<Keyword>> {
        return keywordsRepository.getAllKeywords()
            .map { keywordEntities ->
                keywordEntities.reversed()
            }
    }
}
