package com.example.academy_tbc.presentation.screen.home.model

data class PostUi(
    val id: Int,
    val avatar: String,
    val postDate: String,
    val firstName: String,
    val lastName: String,
    val images: List<String>,
    val commentsCount: Int,
    val likesCount: Int,
    val postDesc: String,
    val canComment: Boolean,
    val canPostPhoto: Boolean
)