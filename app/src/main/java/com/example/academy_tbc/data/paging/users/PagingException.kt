package com.example.academy_tbc.data.paging.users

import com.example.academy_tbc.domain.common.AppError

@Suppress("unused")
class PagingException(val appError: AppError) : Throwable()