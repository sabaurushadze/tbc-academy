package com.example.academy_tbc.domain.repository.home.post

import com.example.academy_tbc.domain.model.home.post.Post
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<Resource<List<Post>>>
}