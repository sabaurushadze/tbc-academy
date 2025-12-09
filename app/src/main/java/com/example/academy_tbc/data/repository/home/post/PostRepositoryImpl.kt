package com.example.academy_tbc.data.repository.home.post

import com.example.academy_tbc.data.mapper.home.post.toDomain
import com.example.academy_tbc.data.room.home.post.PostDao
import com.example.academy_tbc.data.room.home.post.PostEntity
import com.example.academy_tbc.data.service.home.PostApiService
import com.example.academy_tbc.domain.common.AppError
import com.example.academy_tbc.domain.model.home.post.Post
import com.example.academy_tbc.domain.observer.ConnectivityObserver
import com.example.academy_tbc.domain.repository.home.post.PostRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val authService: PostApiService,
    private val postDao: PostDao,
    private val connectivityObserver: ConnectivityObserver,
) : PostRepository {
    override fun getPosts(): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading(true))

        val isConnected = connectivityObserver.isConnected.first()
        if (!isConnected) {
            emit(Resource.Error(AppError.Network))
            emit(Resource.Loading(false))
            return@flow
        }

        try {
            val response = authService.getPosts()

            if (response.isSuccessful) {
                val apiLocations = response.body() ?: emptyList()

                val entities = apiLocations.map { dto ->
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
                        canPostPhoto = dto.canPostPhoto
                    )
                }
                postDao.insertUsers(*entities.toTypedArray())

                emit(Resource.Success(apiLocations.map { it.toDomain() }))
            } else {
                emit(Resource.Error(AppError.Server(response.code())))
            }

        } catch (e: Throwable) {
            val appError = when (e) {
                is SocketTimeoutException -> AppError.Network
                is IOException -> AppError.Network
                else -> AppError.Unknown
            }
            emit(Resource.Error(error = appError))
        } finally {
            emit(Resource.Loading(isLoading = false))
        }
    }
}