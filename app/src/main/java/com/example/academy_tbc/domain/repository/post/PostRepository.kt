package com.example.academy_tbc.domain.repository.post

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.post.Post

interface PostRepository {
    suspend fun getPosts(): Resource<List<Post>, DataError.Network>
}