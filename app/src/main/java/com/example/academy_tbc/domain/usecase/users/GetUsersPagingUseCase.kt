package com.example.academy_tbc.domain.usecase.users

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.academy_tbc.domain.model.users.GetUsers
import com.example.academy_tbc.domain.repository.users.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetUsersPagingUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
) {
    operator fun invoke(config: PagingConfig): Flow<PagingData<GetUsers.GetUser>> {
        return usersRepository.getUsers(config)
    }
}