package com.example.academy_tbc.domain.usecase.users

import com.example.academy_tbc.domain.repository.users.UsersRepository
import javax.inject.Inject

class GetLocalUsersUseCase @Inject constructor(
    private val repo: UsersRepository
) {
    operator fun invoke() = repo.observeUsers()
}