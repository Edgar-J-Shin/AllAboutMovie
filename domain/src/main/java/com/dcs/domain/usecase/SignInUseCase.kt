package com.dcs.domain.usecase

import com.dcs.domain.model.RequestToken
import com.dcs.domain.model.User
import com.dcs.domain.repository.AuthRepository
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Reusable
class SignInUseCase @Inject constructor(
    private val repo: AuthRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(requestToken: RequestToken): Flow<User> {
        return repo.createSessionId(
            requestToken = requestToken
        )
            .flatMapLatest { sessionId ->
                repo.signIn(
                    sessionId = sessionId,
                )
            }
    }
}
