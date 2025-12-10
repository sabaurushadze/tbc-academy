package com.example.academy_tbc.data.room.home.post

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val postDate: Long,
    val avatar: String,
    val firstName: String,
    val lastName: String,
    val images: List<String>,
    val commentsCount: Int,
    val likesCount: Int,
    val postDesc: String,
    val canComment: Boolean,
    val canPostPhoto: Boolean
)