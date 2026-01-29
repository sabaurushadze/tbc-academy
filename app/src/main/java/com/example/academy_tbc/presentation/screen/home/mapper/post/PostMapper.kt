package com.example.academy_tbc.presentation.screen.home.mapper.post

import com.example.academy_tbc.domain.model.post.Post
import com.example.academy_tbc.presentation.screen.home.model.post.UiPost

fun Post.toPresentation(): UiPost {
    return UiPost(
        avatar = avatar,
        postDate = postDate,
        fullName = fullName,
        images = images,
        commentsCount = commentsCount,
        likesCount = likesCount,
        postDesc = postDesc,
        canComment = canComment,
        canPostPhoto = canPostPhoto
    )
}