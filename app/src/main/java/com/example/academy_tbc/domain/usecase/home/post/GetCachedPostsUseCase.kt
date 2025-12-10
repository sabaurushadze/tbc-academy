package com.example.academy_tbc.domain.usecase.home.post

import com.example.academy_tbc.domain.model.home.post.Post
import com.example.academy_tbc.domain.repository.home.post.PostRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCachedPostsUseCase @Inject constructor(
    val postRepository: PostRepository,
) {
    operator fun invoke(): Flow<List<Post>> {
        return postRepository.getCachedPosts()
    }
}