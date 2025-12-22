package com.example.academy_tbc.presentation.screen.events.browse_events

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.databinding.FragmentEventsBinding
import com.example.academy_tbc.domain.model.home.categories.CategoryType
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.dpToPx
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.auth.sign_in.SignInFragmentDirections
import com.example.academy_tbc.presentation.screen.events.browse_events.adapter.EventAdapter
import com.example.academy_tbc.presentation.screen.events.browse_events.adapter.EventCategoryAdapter
import com.example.academy_tbc.presentation.screen.events.browse_events.model.EventUi
import com.example.academy_tbc.presentation.screen.home.trending_events.adapter.HorizontalSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsFragment : BaseFragment<FragmentEventsBinding>(
    FragmentEventsBinding::inflate
) {
    private val viewModel: EventsViewModel by viewModels()

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
        viewModel.onEvent(EventsEvent.SaveCategory(1))
        setUpEventCategoryAdapter()
        setUpEventAdapter()
    }

    override fun listeners() {
        val upcomingEvents: List<EventUi> = listOf(
            EventUi(
                id = 1,
                title = "Team Building",
                description = "Stay connected with upcoming company events and activities.",
                eventTypeId = CategoryType.TEAM_BUILDING,
                startDateTime = "2025-12-21T18:00:00Z",
                endDateTime = "2025-12-21T20:00:00Z",
                location = "Hall A",
                capacity = 50,
                imageUrl = null,
                isActive = true
            ),
            EventUi(
                id = 2,
                title = "Product Launch",
                description = "Stay connected with upcoming company events and activities.",
                eventTypeId = CategoryType.TEAM_BUILDING,
                startDateTime = "2025-12-22T15:30:00Z",
                endDateTime = "2025-12-22T17:00:00Z",
                location = "Room 101",
                capacity = 200,
                imageUrl = null,
                isActive = true
            ),
            EventUi(
                id = 3,
                title = "Workshop",
                description = "Training workshop",
                eventTypeId = CategoryType.TEAM_BUILDING,
                startDateTime = "2025-12-25T09:00:00Z",
                endDateTime = "2025-12-25T12:00:00Z",
                location = "Conference Room",
                capacity = 30,
                imageUrl = null,
                isActive = true
            ),
            EventUi(
                id = 4,
                title = "Team Building",
                description = "Stay connected with upcoming company events and activities.",
                eventTypeId = CategoryType.TEAM_BUILDING,
                startDateTime = "2025-12-21T18:00:00Z",
                endDateTime = "2025-12-21T20:00:00Z",
                location = "Hall A",
                capacity = 50,
                imageUrl = null,
                isActive = true
            ),
            EventUi(
                id = 5,
                title = "Product Launch",
                description = "Stay connected with upcoming company events and activities.",
                eventTypeId = CategoryType.TEAM_BUILDING,
                startDateTime = "2025-12-22T15:30:00Z",
                endDateTime = "2025-12-22T17:00:00Z",
                location = "Room 101",
                capacity = 200,
                imageUrl = null,
                isActive = true
            ),
            EventUi(
                id = 6,
                title = "Workshop",
                description = "Training workshop",
                eventTypeId = CategoryType.TEAM_BUILDING,
                startDateTime = "2025-12-25T09:00:00Z",
                endDateTime = "2025-12-25T12:00:00Z",
                location = "Conference Room",
                capacity = 30,
                imageUrl = null,
                isActive = true
            )
        )

        observeState()
        observeSideEffects()
        eventAdapter.submitList(upcomingEvents)
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
            eventCategoryAdapter.submitList(state.eventCategories)
        }
    }

    companion object {
        const val REQUEST_KEY_EVENT_ID = "request_key_event_id"
        const val BUNDLE_KEY_EVENT_ID = "bundle_key_event_id"
    }

}