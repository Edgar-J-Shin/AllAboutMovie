package com.dcs.domain.usecase

import com.dcs.domain.model.KeywordEntity
import com.dcs.domain.repository.KeywordsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchKeywordsUseCase @Inject constructor(
    private val keywordsRepository: KeywordsRepository,
) {
    operator fun invoke(): Flow<List<KeywordEntity>> {
        return keywordsRepository.getKeywords()
    }
}
