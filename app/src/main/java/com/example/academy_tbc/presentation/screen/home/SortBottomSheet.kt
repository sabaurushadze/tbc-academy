package com.example.academy_tbc.presentation.screen.home

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.academy_tbc.databinding.FragmentSortBottomSheetBinding
import com.example.academy_tbc.presentation.common.view.BaseBottomSheet

class SortBottomSheet : BaseBottomSheet<FragmentSortBottomSheetBinding>(
    FragmentSortBottomSheetBinding::inflate
) {
    override fun listeners() {
        onSortItemClick()
    }

    private fun onSortItemClick() {
        binding.rgSort.setOnCheckedChangeListener { _, checkedId ->
            setFragmentResult(REQUEST_KEY_SORT,
                bundleOf(BUNDLE_KEY_CHECKED_ID to checkedId)
            )
            dismiss()
        }
    }

    companion object {
        const val REQUEST_KEY_SORT = "request_key_sort"
        const val BUNDLE_KEY_CHECKED_ID = "bundle_key_checked_id"
    }

}