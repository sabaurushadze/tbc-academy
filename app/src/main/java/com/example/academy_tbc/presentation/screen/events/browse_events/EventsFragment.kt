package com.example.academy_tbc.presentation.screen.events.browse_events

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.databinding.FragmentEventsBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.dpToPx
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.events.browse_events.EventFilterBottomSheet.Companion.BUNDLE_KEY_DATE
import com.example.academy_tbc.presentation.screen.events.browse_events.EventFilterBottomSheet.Companion.BUNDLE_KEY_LOCATION
import com.example.academy_tbc.presentation.screen.events.browse_events.EventFilterBottomSheet.Companion.BUNDLE_KEY_ONLY_AVAILABLE
import com.example.academy_tbc.presentation.screen.events.browse_events.EventFilterBottomSheet.Companion.REQUEST_KEY_FILTERS
import com.example.academy_tbc.presentation.screen.events.browse_events.categories.adapter.EventCategoryAdapter
import com.example.academy_tbc.presentation.screen.events.browse_events.events.adapter.EventAdapter
import com.example.academy_tbc.presentation.screen.home.trending_events.adapter.HorizontalSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsFragment : BaseFragment<FragmentEventsBinding>(
    FragmentEventsBinding::inflate
) {
    private val viewModel: EventsViewModel by viewModels()
    val args: EventsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(
            REQUEST_KEY_FILTERS,
        ) { _, bundle ->
            val location = bundle.getString(BUNDLE_KEY_LOCATION).orEmpty()
            val onlyAvailable = bundle.getBoolean(BUNDLE_KEY_ONLY_AVAILABLE, false)
            val date = bundle.getString(BUNDLE_KEY_DATE).orEmpty()

            viewModel.onEvent(
                EventsEvent.ApplyFilters(
                    location = location,
                    onlyAvailable = onlyAvailable,
                    date = date
                )
            )
        }

        if (args.selectedCategoryId != -1) {
            viewModel.onEvent(EventsEvent.SaveCategory(args.selectedCategoryId))
        }
    }


    private val eventCategoryAdapter by lazy {
        EventCategoryAdapter(
            onClick = { eventCategoryId ->
                viewModel.onEvent(EventsEvent.SaveCategory(eventCategoryId))
            }
        )
    }

    private val eventAdapter by lazy {
        EventAdapter(
            onClick = { eventId ->
                setFragmentResult(REQUEST_KEY_EVENT_ID, bundleOf(BUNDLE_KEY_EVENT_ID to eventId))
                findNavController().navigate(EventsFragmentDirections.actionEventsFragmentToEventDetailsFragment())
            }
        )

    }

    override fun bind() {
        setUpEventCategoryAdapter()
        setUpEventAdapter()
    }

    override fun listeners() {
        observeState()
        observeSideEffects()
        searchEvent()
        onFilterButtonClick()
    }

    private fun setUpEventCategoryAdapter() = with(binding) {
        rvEventCategories.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = eventCategoryAdapter
            addItemDecoration(
                HorizontalSpacingItemDecoration(
                    4.dpToPx(),
                    addStartSpacing = false,
                    addEndSpacing = false
                )
            )
        }
    }

    private fun setUpEventAdapter() = with(binding) {
        rvEvents.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = eventAdapter
        }
    }


    private fun observeSideEffects() = with(binding) {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                is EventsSideEffect.ShowError ->
                    root.showSnackBar(effect.error.getString(requireContext()))

            }
        }
    }

    private fun observeState() {
        lifecycleCollectLatest(viewModel.state) { state ->
            binding.progressBar.isVisible = state.isLoading
            eventCategoryAdapter.submitList(state.eventCategories)
            eventAdapter.submitList(state.events)
        }
    }

    private fun searchEvent() {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.onEvent(
                EventsEvent.Search(text?.toString().orEmpty())
            )
        }
    }

    private fun onFilterButtonClick() {
        binding.btnFilter.setOnClickListener {
            val filterBottomSheet = EventFilterBottomSheet()
            filterBottomSheet.show(
                parentFragmentManager,
                EventFilterBottomSheet::class.java.simpleName
            )
        }
    }

    companion object {
        const val REQUEST_KEY_EVENT_ID = "request_key_event_id"
        const val BUNDLE_KEY_EVENT_ID = "bundle_key_event_id"
    }

}