package com.example.academy_tbc.presentation.screen.home.model.post

data class UiPost(
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