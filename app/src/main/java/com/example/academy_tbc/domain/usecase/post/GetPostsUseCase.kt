package com.example.academy_tbc.domain.usecase.post

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.post.Post
import com.example.academy_tbc.domain.repository.post.PostRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(): Resource<List<Post>, DataError.Network> {
        return postRepository.getPosts()
    }
}