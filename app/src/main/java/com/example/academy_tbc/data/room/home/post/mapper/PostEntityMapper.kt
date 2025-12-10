package com.example.academy_tbc.data.room.home.post.mapper

import com.example.academy_tbc.data.room.home.post.PostEntity
import com.example.academy_tbc.domain.model.home.post.Post

fun PostEntity.toDomain(): Post =
    Post(
        id = id,
        avatar = avatar,
        postDate = postDate,
        firstName = firstName,
        lastName = lastName,
        images = images,
        commentsCount = commentsCount,
        likesCount = likesCount,
        postDesc = postDesc,
        canComment = canComment,
        canPostPhoto = canPostPhoto
    )