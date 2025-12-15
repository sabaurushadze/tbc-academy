package com.example.academy_tbc.domain.repository.part_details

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.part_details.PartDetail
import kotlinx.coroutines.flow.Flow

interface PartDetailsRepository {
    fun getPartDetails(id: Int): Flow<Resource<PartDetail, ApiError>>
}