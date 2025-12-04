package com.example.academy_tbc.domain.usecase.users

import com.example.academy_tbc.domain.repository.users.UsersRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteUser(id)
}