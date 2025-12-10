package com.example.academy_tbc.domain.model.home.post

data class Post(
    val id: Int,
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