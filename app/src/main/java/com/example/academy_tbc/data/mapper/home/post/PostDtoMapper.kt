package com.example.academy_tbc.data.mapper.home.post

import com.example.academy_tbc.data.model.response.home.post.PostResponseDto
import com.example.academy_tbc.domain.model.home.post.Post

fun PostResponseDto.toDomain(): Post {
    return Post(
        avatar = avatar,
        postDate = postDate,
        firstName = firstName,
        lastName = lastName,
        images = images,
        commentsCount = commentsCount,
        likesCount = likesCount,
        postDesc = postDesc,
        canComment = canComment,
        canPostPhoto = canPostPhoto,
        id = 0,
    )
}