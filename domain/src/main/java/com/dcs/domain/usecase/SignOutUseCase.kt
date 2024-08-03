package com.dcs.domain.usecase

import com.dcs.domain.model.SessionId
import com.dcs.domain.repository.AuthRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class SignOutUseCase @Inject constructor(
    private val repo: AuthRepository,
) {
    operator fun invoke(
        userId: Long,
        sessionId: SessionId,
    ): Flow<Unit> {
        return repo.signOut(
            userId = userId,
            sessionId = sessionId,
        )
    }
}
