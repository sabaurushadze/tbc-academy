package com.example.academy_tbc.presentation.screen.home

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentFilterBottomSheetBinding
import com.example.academy_tbc.presentation.common.view.BaseBottomSheet
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.home.HomeFragment.Companion.BUNDLE_KEY_ID_FILTER
import com.example.academy_tbc.presentation.screen.home.HomeFragment.Companion.REQUEST_KEY_ID_FILTER
import com.example.academy_tbc.presentation.screen.home.adapter.FiltersAdapter
import com.example.academy_tbc.presentation.screen.home.mapper.getFiltersByCategory
import com.example.academy_tbc.presentation.screen.home.model.SelectedFilters

class FilterBottomSheet() : BaseBottomSheet<FragmentFilterBottomSheetBinding>(
    FragmentFilterBottomSheetBinding::inflate
) {

    private val filtersAdapter by lazy {
        FiltersAdapter(
            onFilterChanged = { selectedFilters ->
                currentSelection = selectedFilters
            }
        )
    }

    private var currentSelection: SelectedFilters? = null

    override fun listeners() {
        sendFilterOptionsToParent()
    }

    override fun bind() = with(binding) {
        rvFilters.layoutManager = LinearLayoutManager(requireContext())
        rvFilters.adapter = filtersAdapter
        setFragmentResultListener(REQUEST_KEY_ID_FILTER) { _, bundle ->
            val categoryId = bundle.getInt(BUNDLE_KEY_ID_FILTER)
            filtersAdapter.submitList(getFiltersByCategory(categoryId))
        }
    }

    private fun sendFilterOptionsToParent() = with(binding) {
        btnApply.setOnClickListener {
            val selection = currentSelection

            if (selection != null) {
                val minPrice = selection.minPrice
                val maxPrice = selection.maxPrice
                if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
                    root.showSnackBar(getString(R.string.min_price_cannot_be_greater_than_max_price))
                    return@setOnClickListener
                }
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY_FILTER,
                    bundleOf(
                        BUNDLE_KEY_MIN_PRICE to selection.minPrice,
                        BUNDLE_KEY_MAX_PRICE to selection.maxPrice,
                        BUNDLE_KEY_SELECTED_OPTIONS to selection.selectedOptions
                    )
                )
                dismiss()
            }
        }
    }


    companion object {
        const val REQUEST_KEY_FILTER = "request_key_filter"
        const val BUNDLE_KEY_MIN_PRICE = "min_price"
        const val BUNDLE_KEY_MAX_PRICE = "max_price"
        const val BUNDLE_KEY_SELECTED_OPTIONS = "selected_options"
    }
}