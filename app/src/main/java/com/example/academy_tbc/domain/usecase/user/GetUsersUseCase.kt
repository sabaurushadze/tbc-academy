package com.example.academy_tbc.domain.usecase.user

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.user.User
import com.example.academy_tbc.domain.repository.user.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(query: String?): Resource<List<User>, DataError.Network> {
        return userRepository.getUsers(query)
    }
}