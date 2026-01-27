package com.example.academy_tbc.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.academy_tbc.R
import com.example.academy_tbc.presentation.common.BaseAsyncImage
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
    onShowSnackBar: (String) -> Unit,
) {
    val context = LocalResources.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(
        pageCount = { state.locations.size })
    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeEvent.GetLocations)
    }

    viewModel.sideEffect.collectEvent { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                onShowSnackBar(error)
            }
        }
    }
    HomeContent(
        state = state, onEvent = viewModel::onEvent, pagerState = pagerState
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    pagerState: PagerState,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColor.background),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(
                    start = Dimen.size48,
                    bottom = Dimen.size48
                ),
            text = stringResource(R.string.statistics),
            style = AppTextStyle.body24Bold,
            color = AppColor.onBackground
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            pageSpacing = Dimen.size16,
            contentPadding = PaddingValues(horizontal = Dimen.size48)
        ) { page ->

            val item = state.locations[page]

            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
            val scale = 0.90f + (1 - kotlin.math.abs(pageOffset)) * 0.15f

            LocationItem(
                title = item.title,
                imageUrl = item.image,
                location = item.location,
                altitude = item.altitudeM,
                stars = item.stars,
                price = item.price,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
            )
        }

    }
}

@Composable
private fun LocationItem(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String,
    location: String,
    altitude: String,
    stars: Int,
    price: String,
) {
    Box(
        modifier = modifier
            .clip(AppRadius.radius24)
            .aspectRatio(3f / 5f)
    ) {
        BaseAsyncImage(
            modifier = Modifier.matchParentSize(),
            url = imageUrl,
        )

        Column(
            modifier = Modifier
                .padding(Dimen.size32, vertical = Dimen.size40)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Icon(
                        modifier = Modifier.padding(top = Dimen.size2),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_location),
                        contentDescription = null,
                        tint = AppColor.onBackground
                    )

                    Spacer(modifier = Modifier.width(Dimen.size8))

                    Text(
                        text = location,
                        style = AppTextStyle.body20Bold,
                        color = AppColor.onBackground
                    )
                }

                Row() {
                    Icon(
                        modifier = Modifier.padding(top = Dimen.size2),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_altitude),
                        contentDescription = null,
                        tint = AppColor.onBackground
                    )

                    Spacer(modifier = Modifier.width(Dimen.size8))

                    Text(
                        text = altitude,
                        style = AppTextStyle.body20Bold,
                        color = AppColor.onBackground
                    )
                }

            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = AppTextStyle.title32Bold,
                    color = AppColor.onBackground,
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth(0.6f)
                )

                Spacer(modifier = Modifier.height(Dimen.size16))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = price, style = AppTextStyle.body20Bold, color = AppColor.onBackground
                    )
                    RatingStars(stars)
                }

                Spacer(modifier = Modifier.height(Dimen.size40))
            }


        }


    }
}


@Composable
private fun RatingStars(
    rating: Int,
    maxRating: Int = 5,
) {
    Row {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= rating) ImageVector.vectorResource(R.drawable.ic_star_filled)
                else ImageVector.vectorResource(R.drawable.ic_star_outlined),
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderItemPreview() {
    AppTheme {
        LocationItem(
            title = "Natural walk to the top",
            imageUrl = "",
            location = "Barcelona",
            altitude = "2500",
            stars = 4,
            price = "$120"
        )
    }
}