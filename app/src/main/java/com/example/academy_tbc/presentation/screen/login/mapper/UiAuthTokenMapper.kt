package com.example.academy_tbc.presentation.screen.login.mapper

import com.example.academy_tbc.domain.common.FromDomainMapper
import com.example.academy_tbc.domain.model.login.AuthToken
import com.example.academy_tbc.presentation.screen.login.model.UiAuthToken
import javax.inject.Inject

class UiAuthTokenMapper @Inject constructor() : FromDomainMapper<UiAuthToken, AuthToken> {

    override fun mapFromDomain(data: AuthToken): UiAuthToken {
        return UiAuthToken(token = data.token)
    }
}