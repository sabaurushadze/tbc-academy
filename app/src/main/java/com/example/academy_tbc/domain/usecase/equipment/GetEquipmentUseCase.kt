package com.example.academy_tbc.domain.usecase.equipment

import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.equipment.Equipment
import com.example.academy_tbc.domain.repository.equipment.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEquipmentUseCase @Inject constructor(
    private val equipmentRepository: EquipmentRepository,
) {

    operator fun invoke(): Flow<Resource<List<Equipment>>> {
        return equipmentRepository.getEquipment()
    }
}