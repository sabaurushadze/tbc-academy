package com.example.academy_tbc.presentation.screen.events.browse_events

import android.R
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.academy_tbc.databinding.BottomSheetFilterEventsBinding
import com.example.academy_tbc.presentation.common.view.BaseBottomSheet

class EventFilterBottomSheet : BaseBottomSheet<BottomSheetFilterEventsBinding>(
    BottomSheetFilterEventsBinding::inflate
) {
    override fun bind() {
        val dateOptions = listOf("All", "Today", "This week", "This month")
        val adapter =
            ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, dateOptions)
        binding.spinnerDate.setAdapter(adapter)
    }

    override fun listeners() {
        binding.btnApply.setOnClickListener {
            val location = binding.etFilterLocation.text.toString()
            val onlyAvailable = binding.switchAvailableEvents.isChecked
            val date = binding.spinnerDate.text.toString()

            val bundle = bundleOf(
                BUNDLE_KEY_LOCATION to location,
                BUNDLE_KEY_ONLY_AVAILABLE to onlyAvailable,
                BUNDLE_KEY_DATE to date
            )
            setFragmentResult(
                REQUEST_KEY_FILTERS,
                bundle
            )
            dismiss()
        }
    }

    companion object {
        const val REQUEST_KEY_FILTERS = "request_key_filters"

        const val BUNDLE_KEY_LOCATION = "location"
        const val BUNDLE_KEY_ONLY_AVAILABLE = "onlyAvailable"
        const val BUNDLE_KEY_DATE = "date"
    }
}