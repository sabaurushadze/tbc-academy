package com.example.academy_tbc.data.service.home

import com.example.academy_tbc.data.model.response.home.post.PostResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface PostApiService {
    @GET("1e3f40b1-19a5-4986-ad60-fdc80c27234b")
    suspend fun getPosts(): Response<List<PostResponseDto>>
}