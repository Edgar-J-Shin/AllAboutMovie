package com.dcs.domain.usecase

import com.dcs.domain.model.User
import com.dcs.domain.repository.AuthRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class GetUserUseCase @Inject constructor(
    private val repo: AuthRepository,
) {
    operator fun invoke(): Flow<User?> {
        return repo.getUser()
    }
}
