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
        userTmdbId: Long,
        sessionId: SessionId,
    ): Flow<Unit> {
        return repo.signOut(
            userTmdbId = userTmdbId,
            sessionId = sessionId,
        )
    }
}
