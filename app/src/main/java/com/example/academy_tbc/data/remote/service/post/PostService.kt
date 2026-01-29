package com.example.academy_tbc.data.remote.service.post

import com.example.academy_tbc.data.remote.dto.response.posts.PostResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface PostService {

    @GET(POSTS)
    suspend fun getPosts(): Response<List<PostResponseDto>>

    companion object {
        private const val POSTS = "posts"
    }

}