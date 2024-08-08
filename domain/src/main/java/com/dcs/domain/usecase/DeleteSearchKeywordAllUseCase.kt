package com.dcs.domain.usecase

import com.dcs.domain.repository.KeywordsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteSearchKeywordAllUseCase @Inject constructor(
    private val keywordsRepository: KeywordsRepository,
) {
    operator fun invoke(): Flow<Unit> {
        return keywordsRepository.deleteAll()
    }
}
