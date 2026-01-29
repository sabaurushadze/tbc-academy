package com.example.academy_tbc.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.academy_tbc.R
import com.example.academy_tbc.presentation.common.BaseAsyncImage
import com.example.academy_tbc.presentation.compositionlocal.LocalSnackbarHostState
import com.example.academy_tbc.presentation.extension.collectEvent
import com.example.academy_tbc.presentation.theme.AppColor
import com.example.academy_tbc.presentation.theme.AppRadius
import com.example.academy_tbc.presentation.theme.AppTextStyle
import com.example.academy_tbc.presentation.theme.AppTheme
import com.example.academy_tbc.presentation.theme.Dimen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeEvent.GetPosts)
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeEvent.GetStories)
    }

    viewModel.sideEffect.collectEvent { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }
        }
    }
    HomeContent(state = state)
}

@Composable
private fun HomeContent(
    state: HomeState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor.background)
    ) {
        LazyColumn(

            verticalArrangement = Arrangement.spacedBy(Dimen.size16),
            contentPadding = PaddingValues(vertical = Dimen.size48)
        ) {
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = Dimen.size16),
                    horizontalArrangement = Arrangement.spacedBy(Dimen.size16)
                ) {
                    items(state.stories) { story ->
                        StoryItem(
                            title = story.title, imageUrl = story.cover
                        )
                    }
                }
            }

            items(state.posts) { post ->
                Box(
                    modifier = Modifier.padding(horizontal = Dimen.size16)
                ) {
                    PostItem(
                        avatarUrl = post.avatar,
                        fullName = post.fullName,
                        postDate = post.postDate,
                        postDesc = post.postDesc,
                        images = post.images,
                        commentsCount = post.commentsCount,
                        likesCount = post.likesCount,
                        canComment = post.canComment,
                        canPostPhoto = post.canPostPhoto
                    )
                }

            }
        }
    }


}

@Composable
private fun StoryItem(
    title: String,
    imageUrl: String,
) {
    Box(
        modifier = Modifier
            .height(Dimen.size200)
            .width(Dimen.size144)
            .clip(AppRadius.radius24)
    ) {
        BaseAsyncImage(
            modifier = Modifier,
            url = imageUrl,
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = Dimen.size24,
                    bottom = Dimen.size24,
                    end = Dimen.size24
                ),
            text = title,
            style = AppTextStyle.body14Bold,
            color = AppColor.onSurfaceLight
        )

    }
}

@Composable
private fun PostItem(
    avatarUrl: String,
    fullName: String,
    postDate: String,
    postDesc: String,
    images: List<String>,
    commentsCount: String,
    likesCount: String,
    canComment: Boolean,
    canPostPhoto: Boolean,
) {
    Box(
        modifier = Modifier
            .clip(AppRadius.radius24)
            .background(AppColor.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.size16)
        ) {
            PostDetailsSection(
                avatarUrl = avatarUrl, fullName = fullName, postDate = postDate, postDesc = postDesc
            )

            ImageGridSection(images)

            Spacer(modifier = Modifier.height(Dimen.size16))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(), color = AppColor.onSurface
            )

            Spacer(modifier = Modifier.height(Dimen.size16))

            PostActionsSection(
                commentsCount = commentsCount, likesCount = likesCount
            )

            Spacer(modifier = Modifier.height(Dimen.size20))


            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(), color = AppColor.onSurface
            )

            Spacer(modifier = Modifier.height(Dimen.size20))

            CommentSection(
                canComment = canComment, canPostPhoto = canPostPhoto, avatarUrl = avatarUrl
            )
        }

    }
}

@Composable
fun PostDetailsSection(
    avatarUrl: String,
    fullName: String,
    postDate: String,
    postDesc: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(Dimen.size48)
                    .clip(AppRadius.radius50)
            ) {
                BaseAsyncImage(
                    modifier = Modifier.matchParentSize(),
                    url = avatarUrl,
                    clipShape = AppRadius.radius50
                )
            }

            Spacer(modifier = Modifier.width(Dimen.size24))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = fullName,
                    style = AppTextStyle.body16Bold,
                    color = AppColor.onSurfaceLight
                )

                Spacer(modifier = Modifier.height(Dimen.size6))

                Text(
                    text = postDate, style = AppTextStyle.body14Normal, color = AppColor.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimen.size12))

        Text(
            text = postDesc, style = AppTextStyle.body14Normal, color = AppColor.onSurfaceLight
        )

        Spacer(modifier = Modifier.height(Dimen.size12))
    }

}

@Composable
private fun ImageGridSection(
    images: List<String>,
) {
    when (images.size) {
        0, 1 -> BaseAsyncImage(
            modifier = Modifier
                .height(Dimen.size154)
                .fillMaxWidth(),
            url = images.getOrNull(0) ?: ""
        )

        2 -> Row {
            images.forEach { url ->
                BaseAsyncImage(
                    modifier = Modifier
                        .weight(1f)
                        .height(71.dp)
                        .padding(horizontal = 4.dp),
                    url = url
                )
            }
        }

        3 -> Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimen.size154)
        ) {
            BaseAsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(2.dp),
                url = images.getOrNull(0) ?: ""
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                BaseAsyncImage(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(2.dp),
                    url = images.getOrNull(1) ?: ""
                )
                BaseAsyncImage(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(2.dp),
                    url = images.getOrNull(2) ?: ""
                )
            }
        }

        else -> {}
    }
}

@Composable
private fun PostActionsSection(
    commentsCount: String,
    likesCount: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconAndText(
            iconRes = R.drawable.ic_comment,
            text = "$commentsCount Comments",
        )
        IconAndText(
            iconRes = R.drawable.ic_heart,
            text = "$likesCount Likes",
        )
        IconAndText(
            iconRes = R.drawable.ic_share,
            text = "Share",
        )
    }
}

@Composable
private fun IconAndText(
    iconRes: Int,
    text: String,
    tintColor: Color = AppColor.onSurface,
) {
    Row() {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
            tint = tintColor
        )

        Spacer(modifier = Modifier.width(Dimen.size8))

        Text(
            text = text, style = AppTextStyle.body14Normal, color = AppColor.onSurface
        )

    }
}

@Composable
private fun CommentSection(
    canComment: Boolean,
    canPostPhoto: Boolean,
    avatarUrl: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(Dimen.size50)
                .clip(CircleShape)
        ) {
            BaseAsyncImage(
                modifier = Modifier.matchParentSize(),
                url = avatarUrl,
                clipShape = AppRadius.radius50
            )
        }

        Spacer(modifier = Modifier.width(Dimen.size12))

        TextField(
            modifier = Modifier
                .height(Dimen.size48)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = AppColor.onSurfaceContainer,
                focusedIndicatorColor = AppColor.onSurfaceContainer,
                unfocusedIndicatorColor = AppColor.onSurfaceContainer,
                disabledContainerColor = AppColor.onSurfaceContainer
            ),
            shape = AppRadius.radius12,
            value = "",
            placeholder = {
                Text(
                    text = stringResource(R.string.write_comment),
                    style = AppTextStyle.body14Normal,
                    color = AppColor.onSurface
                )
            },
            enabled = canComment,
            trailingIcon = {
                if (canPostPhoto) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_attach),
                        tint = AppColor.onSurface,
                        contentDescription = null
                    )
                }
            },
            onValueChange = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PersonDetailsPreview() {
    AppTheme {
        PostDetailsSection(
            avatarUrl = "",
            fullName = "John Doe",
            postDate = "24 December at 3:33 AM",
            postDesc = "We’re interested in your ideas and would be glad to build something bigger out of it. "
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OrderItemPreview() {
    AppTheme {
        StoryItem(
            title = "Tbilisi",
            imageUrl = "",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OPostItemPreview() {
    AppTheme {
        PostItem(
            avatarUrl = "",
            fullName = "Alice Smith",
            postDate = "20 april at 2:22 PM",
            postDesc = "BLABLALBLA BLEBELBEL BLUBLUBLU",
            images = listOf("123", "123", "123"),
            commentsCount = "56",
            likesCount = "23",
            canComment = true,
            canPostPhoto = true
        )
    }
}

