package com.example.academy_tbc.domain.usecase.pc_parts

import javax.inject.Inject

class CalculateSalePriceUseCase @Inject constructor (){
    operator fun invoke(price: Float, discount: Int): Float {
        if (discount <= 0) return price
        val discountAmount = price * discount / 100
        return price - discountAmount
    }
}