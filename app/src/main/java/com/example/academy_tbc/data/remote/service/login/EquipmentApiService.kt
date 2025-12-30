package com.example.academy_tbc.data.remote.service.login

import com.example.academy_tbc.data.remote.dto.response.equipment.EquipmentResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface EquipmentApiService {
    @GET(EQUIPMENT)
    suspend fun login(): Response<List<EquipmentResponseDto>>

    companion object {
        private const val EQUIPMENT = "https://mocki.io/v1/0c08be03-49c2-493b-951c-6ba8a397dc72"
    }
}