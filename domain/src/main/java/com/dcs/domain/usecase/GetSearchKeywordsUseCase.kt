package com.dcs.domain.usecase

import com.dcs.domain.model.Keyword
import com.dcs.domain.repository.KeywordsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchKeywordsUseCase @Inject constructor(
    private val keywordsRepository: KeywordsRepository,
) {
    operator fun invoke(): Flow<List<Keyword>> {
        return keywordsRepository.getKeywords()
    }
}
