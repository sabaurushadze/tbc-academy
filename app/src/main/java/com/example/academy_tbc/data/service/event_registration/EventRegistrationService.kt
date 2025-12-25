package com.example.academy_tbc.data.service.event_registration

import com.example.academy_tbc.data.dto.request.event_registration.EventRegistrationRequestDto
import com.example.academy_tbc.data.dto.response.auth.event_registration.EventRegistrationResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventRegistrationService {
    @POST("/api/Registration/register")
    suspend fun registerEvent(
        @Body body: EventRegistrationRequestDto,
    ): Response<Unit>


    @DELETE("/api/Registration/unregister/{eventId}")
    suspend fun unregisterEvent(
        @Path("eventId") eventId: Int,
    ): Response<Unit>

    @GET("/api/Registration/check/{eventId}")
    suspend fun checkEventRegistration(
        @Path("eventId") eventId: Int,
    ): Response<EventRegistrationResponseDto>
}
