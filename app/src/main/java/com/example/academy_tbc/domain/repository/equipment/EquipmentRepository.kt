package com.example.academy_tbc.domain.repository.equipment

import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.equipment.Equipment
import kotlinx.coroutines.flow.Flow

interface EquipmentRepository {
    fun getEquipment(): Flow<Resource<List<Equipment>>>
}