package com.example.academy_tbc.presentation.screen.home

import androidx.core.os.bundleOf
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentFilterBottomSheetBinding
import com.example.academy_tbc.domain.model.pc_parts.Condition
import com.example.academy_tbc.presentation.common.view.BaseBottomSheet
import com.example.academy_tbc.presentation.extension.showSnackBar

class FilterBottomSheet() : BaseBottomSheet<FragmentFilterBottomSheetBinding>(
    FragmentFilterBottomSheetBinding::inflate
) {
    override fun listeners() {
        sendFilterOptionsToParent()
    }

    private fun sendFilterOptionsToParent() = with(binding) {
        btnApply.setOnClickListener {
            val minPriceText = etMinPrice.text?.toString()?.trim()
            val maxPriceText = etMaxPrice.text?.toString()?.trim()

            val minPrice = minPriceText?.toFloatOrNull()
            val maxPrice = maxPriceText?.toFloatOrNull()

            val condition = when(rgCondition.checkedRadioButtonId) {
                R.id.rbNew -> Condition.NEW
                R.id.rbOpenBox -> Condition.OPEN_BOX
                R.id.rbPreOwned -> Condition.PRE_OWNED
                else -> Condition.NEW
            }

            if (minPrice == null || maxPrice == null) {
                root.showSnackBar(getString(R.string.please_enter_valid_numbers_for_price))
                return@setOnClickListener
            }

            if (minPrice < 0 || maxPrice < 0) {
                root.showSnackBar(getString(R.string.price_cannot_be_negative))
                return@setOnClickListener
            }

            if (minPrice > maxPrice) {
                root.showSnackBar(getString(R.string.min_price_cannot_be_greater_than_max_price))
                return@setOnClickListener
            }

            parentFragmentManager.setFragmentResult(
                REQUEST_KEY_FILTER,
                bundleOf(
                    BUNDLE_KEY_MIN_PRICE to minPrice,
                    BUNDLE_KEY_MAX_PRICE to maxPrice,
                    BUNDLE_KEY_CONDITION to condition.apiValue,
                )
            )

            dismiss()
        }
    }


    companion object {
        const val REQUEST_KEY_FILTER = "request_key_filter"
        const val BUNDLE_KEY_MIN_PRICE = "min_price"
        const val BUNDLE_KEY_MAX_PRICE = "max_price"
        const val BUNDLE_KEY_CONDITION = "condition"
    }
}