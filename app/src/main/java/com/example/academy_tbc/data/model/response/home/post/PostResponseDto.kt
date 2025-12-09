package com.example.academy_tbc.data.model.response.home.post

import kotlinx.serialization.Serializable

@Serializable
data class PostResponseDto(
    val avatar: String,
    val postDate: Long,
    val firstName: String,
    val lastName: String,
    val images: List<String>,
    val commentsCount: Int,
    val likesCount: Int,
    val postDesc: String,
    val canComment: Boolean,
    val canPostPhoto: Boolean
)