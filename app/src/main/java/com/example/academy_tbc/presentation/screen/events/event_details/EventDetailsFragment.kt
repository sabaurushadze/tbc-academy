package com.example.academy_tbc.presentation.screen.events.event_details

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentEventDetailBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.loadImage
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.events.browse_events.EventsFragment.Companion.BUNDLE_KEY_EVENT_ID
import com.example.academy_tbc.presentation.screen.events.browse_events.EventsFragment.Companion.REQUEST_KEY_EVENT_ID
import com.example.academy_tbc.presentation.screen.events.event_details.adapter.AgendaAdapter
import com.example.academy_tbc.presentation.screen.events.event_details.adapter.FeaturedSpeakersAdapter
import com.example.academy_tbc.presentation.screen.home.HomeFragment.Companion.BUNDLE_KEY_UPCOMING_EVENT_ID
import com.example.academy_tbc.presentation.screen.home.HomeFragment.Companion.REQUEST_KEY_UPCOMING_EVENT_ID
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
        setFragmentResultListener(REQUEST_KEY_EVENT_ID) { _, bundle ->
            val eventId = bundle.getInt(BUNDLE_KEY_EVENT_ID)
            viewModel.onEvent(EventDetailsEvent.GetEventById(eventId))
            viewModel.onEvent(EventDetailsEvent.CheckRegisterEvent(eventId))
        }
        setFragmentResultListener(REQUEST_KEY_UPCOMING_EVENT_ID) { _, bundle ->
            val upcomingEventId = bundle.getInt(BUNDLE_KEY_UPCOMING_EVENT_ID)
            viewModel.onEvent(EventDetailsEvent.GetEventById(upcomingEventId))
            viewModel.onEvent(EventDetailsEvent.CheckRegisterEvent(upcomingEventId))
        }
        setFragmentResultListener("request_key_trending_event_id") { _, bundle ->
            val upcomingEventId = bundle.getInt("bundle_key_trending_event_id")
            viewModel.onEvent(EventDetailsEvent.GetEventById(upcomingEventId))
            viewModel.onEvent(EventDetailsEvent.CheckRegisterEvent(upcomingEventId))
        }
    }

    override fun bind() {
        setUpAgendaAdapter()
        setUpFeaturedSpeakersAdapter()
    }

    override fun listeners() {
        observeState()
        observeSideEffects()
        goBackToBrowseEvents()
        onRegisterNowButtonClick()
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

    private fun observeState() = with(binding) {
        lifecycleCollectLatest(viewModel.state) { state ->
            state.event?.apply {
                ivEvent.loadImage(imageUrl)
                tvEventCategory.text = categoryTitle
                tvEventTitle.text = title
                tvEventDescription.text = description
                tvEventDate.text = date
                tvEventTime.text = time
                tvLocation.text = location
                tvRegisteredAmount.text =
                    getString(R.string.registered_spots_left, currentCapacity, availableSlots)
                tvRegistrationClosingFullDate.text = registrationClosingDate
            }

            agendaAdapter.submitList(state.event?.agendas)
            featuredSpeakersAdapter.submitList(state.event?.featuredSpeakers)

            updateRegisterButton(state.isRegistered)
        }
    }

    private fun updateRegisterButton(isRegistered: Boolean) {
        val buttonColor = if (isRegistered) {
            resources.getColor(R.color.light_gray, null)
        } else {
            resources.getColor(R.color.primary, null)
        }
        binding.btnEventRegister.backgroundTintList = ColorStateList.valueOf(buttonColor)

        binding.btnEventRegister.text = if (isRegistered) {
            getString(R.string.already_registered)
        } else {
            getString(R.string.register_now)
        }
    }

    private fun onRegisterNowButtonClick() {
        binding.btnEventRegister.setOnClickListener {
            val eventId = viewModel.state.value.event?.id
            eventId?.let {
                viewModel.onEvent(EventDetailsEvent.ToggleRegistration(eventId))
            }
        }
    }

    private fun goBackToBrowseEvents() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}