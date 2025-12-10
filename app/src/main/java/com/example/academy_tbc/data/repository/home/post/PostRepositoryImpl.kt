package com.example.academy_tbc.data.repository.home.post

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.mapper.home.post.toDomain
import com.example.academy_tbc.data.room.home.post.PostDao
import com.example.academy_tbc.data.room.home.post.PostEntity
import com.example.academy_tbc.data.room.home.post.mapper.toDomain
import com.example.academy_tbc.data.service.home.PostApiService
import com.example.academy_tbc.domain.model.home.post.Post
import com.example.academy_tbc.domain.repository.home.post.PostRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val authService: PostApiService,
    private val postDao: PostDao,
    private val responseHandler: ResponseHandler,
) : PostRepository {

    override fun getCachedPosts(): Flow<List<Post>> {
        return postDao.getAll().map { list -> list.map { it.toDomain() } }
    }

    override fun getPosts(): Flow<Resource<List<Post>>> = flow {

        responseHandler.safeApiCall {
            authService.getPosts()
        }.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    val posts = resource.data

                    val entities = posts.map { dto ->
                        PostEntity(
                            avatar = dto.avatar,
                            postDate = dto.postDate,
                            firstName = dto.firstName,
                            lastName = dto.lastName,
                            images = dto.images,
                            commentsCount = dto.commentsCount,
                            likesCount = dto.likesCount,
                            postDesc = dto.postDesc,
                            canComment = dto.canComment,
                            canPostPhoto = dto.canPostPhoto,
                            id = 0
                        )
                    }
                    postDao.insertUsers(*entities.toTypedArray())

                    emit(Resource.Success(posts.map { it.toDomain() }))
                }

                is Resource.Error -> emit(resource)
                is Resource.Loading -> emit(resource)
            }
        }
    }
}