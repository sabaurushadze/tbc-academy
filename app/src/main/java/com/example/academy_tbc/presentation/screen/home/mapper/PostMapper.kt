package com.example.academy_tbc.presentation.screen.home.mapper

import com.example.academy_tbc.domain.model.home.post.Post
import com.example.academy_tbc.presentation.screen.home.model.PostUi
import com.example.academy_tbc.presentation.util.DateFormatter.toPostDateString

fun Post.toUi(): PostUi {
    return PostUi(
        avatar = avatar,
        postDate = postDate.toPostDateString(),
        firstName = firstName,
        lastName = lastName,
        images = images,
        commentsCount = commentsCount,
        likesCount = likesCount,
        postDesc = postDesc,
        canComment = canComment,
        canPostPhoto = canPostPhoto
    )
}