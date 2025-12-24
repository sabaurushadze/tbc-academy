package com.example.academy_tbc.presentation.screen.events.event_details.mapper

import com.example.academy_tbc.domain.model.home.upcoming_events.FeaturedSpeaker
import com.example.academy_tbc.presentation.screen.events.event_details.model.FeaturedSpeakerUi

fun FeaturedSpeaker.toPresentation() = FeaturedSpeakerUi(
    id = id,
    photoUrl = photoUrl,
    name = name,
    role = role
)