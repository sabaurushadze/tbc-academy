package com.example.academy_tbc.presentation.screen.events.event_details

import android.os.Bundle
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.databinding.FragmentEventDetailBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.events.browse_events.EventsFragment.Companion.BUNDLE_KEY_EVENT_ID
import com.example.academy_tbc.presentation.screen.events.browse_events.EventsFragment.Companion.REQUEST_KEY_EVENT_ID
import com.example.academy_tbc.presentation.screen.events.event_details.adapter.AgendaAdapter
import com.example.academy_tbc.presentation.screen.events.event_details.adapter.FeaturedSpeakersAdapter
import com.example.academy_tbc.presentation.screen.events.event_details.model.AgendaUi
import com.example.academy_tbc.presentation.screen.events.event_details.model.FeaturedSpeakerUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventDetailsFragment : BaseFragment<FragmentEventDetailBinding>(
    FragmentEventDetailBinding::inflate
) {
    private val viewModel: EventDetailsViewModel by viewModels()

    private val agendaAdapter by lazy {
        AgendaAdapter()
    }

    private val featuredSpeakersAdapter by lazy {
        FeaturedSpeakersAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_KEY_EVENT_ID) { requestKey, bundle ->
            val eventId = bundle.getInt(BUNDLE_KEY_EVENT_ID)

        }
    }

    override fun bind() {
        setUpAgendaAdapter()
        setUpFeaturedSpeakersAdapter()
    }

    override fun listeners() {
        val upcomingEvents: List<AgendaUi> = listOf(
            AgendaUi(
                id = 1,
                title = "Team Building",
                description = "Stay connected with upcoming company events and activities.",
                time = "2025-12-21T18:00:00Z",
            ),
            AgendaUi(
                id = 2,
                title = "Team Building",
                description = "Stay connected with upcoming company events and activities.",
                time = "2025-12-21T18:00:00Z",
            ),
            AgendaUi(
                id = 3,
                title = "Team Building",
                description = "Stay connected with upcoming company events and activities.",
                time = "2025-12-21T18:00:00Z",
            )
        )
        val featuredSpeakers: List<FeaturedSpeakerUi> = listOf(
            FeaturedSpeakerUi(
                id = 1,
                imageUrl = "",
                name = "Sarah Johnson",
                role = "Lead Corporate Trainer"
            ),
            FeaturedSpeakerUi(
                id = 2,
                imageUrl = "",
                name = "Sarah Johnson",
                role = "Lead Corporate Trainer"
            ),
            FeaturedSpeakerUi(
                id = 3,
                imageUrl = "",
                name = "Sarah Johnson",
                role = "Lead Corporate Trainer"
            ),
        )
//        agendaAdapter.submitList(upcomingEvents)
        observeState()
        observeSideEffects()
//        agendaAdapter.submitList(upcomingEvents)
//        featuredSpeakersAdapter.submitList(featuredSpeakers)
    }

    private fun setUpAgendaAdapter() = with(binding) {
        rvAgendas.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = agendaAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun setUpFeaturedSpeakersAdapter() = with(binding) {
        rvFeaturedSpeakers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = featuredSpeakersAdapter
            isNestedScrollingEnabled = false
        }
    }


    private fun observeSideEffects() = with(binding) {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                is EventDetailsSideEffect.ShowError ->
                    root.showSnackBar(effect.error.getString(requireContext()))

            }
        }
    }

    private fun observeState() {
        lifecycleCollectLatest(viewModel.state) { state ->

        }
    }

}