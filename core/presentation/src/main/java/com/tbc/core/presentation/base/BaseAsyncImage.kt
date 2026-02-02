package com.tbc.core.presentation.base

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tbc.core_ui.theme.AppRadius
import com.tbc.resource.R

@Composable
fun BaseAsyncImage(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Crop,
    placeholderRes: Int = R.drawable.placeholder,
    errorRes: Int = R.drawable.placeholder,
    clipShape: RoundedCornerShape = AppRadius.radius16,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        placeholder = painterResource(placeholderRes),
        error = painterResource(errorRes),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier.clip(
            clipShape
        )
    )
}