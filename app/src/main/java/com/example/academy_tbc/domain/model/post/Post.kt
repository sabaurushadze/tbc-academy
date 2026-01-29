package com.example.academy_tbc.domain.model.post

data class Post(
    val avatar: String,
    val postDate: String,
    val fullName: String,
    val images: List<String>,
    val commentsCount: String,
    val likesCount: String,
    val postDesc: String,
    val canComment: Boolean,
    val canPostPhoto: Boolean
)