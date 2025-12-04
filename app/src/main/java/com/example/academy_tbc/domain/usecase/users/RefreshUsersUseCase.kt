package com.example.academy_tbc.domain.usecase.users

import com.example.academy_tbc.domain.repository.users.UsersRepository
import javax.inject.Inject

class RefreshUsersUseCase @Inject constructor(
    private val repo: UsersRepository,
) {
    suspend operator fun invoke() = repo.refreshUsers()
}