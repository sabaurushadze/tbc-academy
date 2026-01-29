package com.example.academy_tbc.data.repository.post

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.remote.mapper.posts.toDomain
import com.example.academy_tbc.data.remote.service.post.PostService
import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.mapList
import com.example.academy_tbc.domain.model.post.Post
import com.example.academy_tbc.domain.repository.post.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService,
    private val responseHandler: ApiResponseHandler,
) : PostRepository {
    override suspend fun getPosts(): Resource<List<Post>, DataError.Network> {
        return responseHandler.safeApiCall {
            postService.getPosts()
        }.mapList { it.toDomain() }
    }
}