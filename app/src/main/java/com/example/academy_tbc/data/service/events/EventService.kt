package com.example.academy_tbc.data.service.events

import com.example.academy_tbc.data.dto.response.home.events.EventsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventService {
    @GET("/api/Events/sorted-by-upcoming-date")
    suspend fun getEvents(
        @Query("n") n: Int? = null
    ): Response<List<EventsResponseDto>>

    @GET("/api/Events/sorted-by-popularity")
    suspend fun getEventsByPopularity(
        @Query("n") n: Int? = null
    ): Response<List<EventsResponseDto>>

    @GET("/api/Events/{id}")
    suspend fun getEventById(
        @Path("id") id: Int
    ): Response<EventsResponseDto>

}