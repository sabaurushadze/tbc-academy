package com.example.academy_tbc.presentation.screen.home.mapper

import com.example.academy_tbc.domain.common.FromDomainMapper
import com.example.academy_tbc.domain.model.home.GetUsers
import com.example.academy_tbc.domain.model.login.AuthToken
import com.example.academy_tbc.presentation.screen.home.model.UserModel
import com.example.academy_tbc.presentation.screen.login.model.UiAuthToken
import javax.inject.Inject

class UiUserMapper @Inject constructor() : FromDomainMapper<UserModel.User, GetUsers.GetUser> {

    override fun mapFromDomain(data: GetUsers.GetUser): UserModel.User {
        return UserModel.User(
            id = data.id,
            email = data.email,
            firstName = data.firstName,
            lastName = data.lastName,
            avatar = data.avatar
        )
    }
}