package com.example.academy_tbc.data.service.events

import com.example.academy_tbc.data.dto.response.home.events.EventsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EventService {
    @GET("/api/v1/events")
    suspend fun getEvents(): Response<List<EventsResponseDto>>

    @GET("/api/v1/events/{id}")
    suspend fun getEventById(
        @Path("id") id: Int
    ): Response<EventsResponseDto>
}