package com.dcs.domain.usecase

import com.dcs.domain.model.RequestToken
import com.dcs.domain.repository.AuthRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class CreateRequestTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    operator fun invoke(): Flow<RequestToken> {
        return authRepository.createRequestToken()
    }
}
