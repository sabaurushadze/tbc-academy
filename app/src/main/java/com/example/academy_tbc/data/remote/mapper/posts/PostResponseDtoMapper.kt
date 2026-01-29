package com.example.academy_tbc.data.remote.mapper.posts

import com.example.academy_tbc.data.remote.dto.response.posts.PostResponseDto
import com.example.academy_tbc.domain.model.post.Post
import com.example.academy_tbc.presentation.util.DateFormatter

fun PostResponseDto.toDomain(): Post {
    return Post(
        avatar = avatar,
        postDate = DateFormatter.formatDateTime(postDate),
        fullName = "$firstName $lastName",
        images = images,
        commentsCount = commentsCount.toString(),
        likesCount = likesCount.toString(),
        postDesc = postDesc,
        canComment = canComment,
        canPostPhoto = canPostPhoto
    )
}