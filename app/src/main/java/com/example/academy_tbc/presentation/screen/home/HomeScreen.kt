package com.example.academy_tbc.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.academy_tbc.R
import com.example.academy_tbc.presentation.theme.Background
import com.example.academy_tbc.presentation.theme.Black
import com.example.academy_tbc.presentation.theme.Gray
import com.example.academy_tbc.presentation.theme.MyApplicationTheme
import com.example.academy_tbc.presentation.theme.OnPrimaryDisabled
import com.example.academy_tbc.presentation.theme.Primary
import com.example.academy_tbc.presentation.theme.PrimaryDisabled
import com.example.academy_tbc.presentation.theme.Red
import com.example.academy_tbc.presentation.theme.White

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onShowSnackBar: (String) -> Unit,
) {
    val context = LocalResources.current
    val state by viewModel.state.collectAsStateWithLifecycle()



    HomeContent(
        state = state, onEvent = viewModel::onEvent
    )

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.ShowSnackBar -> {
                    val error = context.getString(sideEffect.errorRes)
                    onShowSnackBar(error)
                }
            }
        }
    }

}

@Composable
private fun HomeContent(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
) {
    if (!state.isCategoryOrOutfitReady) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .align(Alignment.Center),
                color = Black
            )

        }
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .background(Background),
            columns = GridCells.Fixed(2),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(state.categories) { category ->
                        CategoryItem(
                            text = category.name,
                            isSelected = state.selectedCategoryId == category.id,
                            onCategoryClick = { onEvent(HomeEvent.CategoryClicked(category.id)) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            itemsIndexed(state.outfits) { index, outfit ->
                val column = index % 2
                val startPadding = if (column == 0) 16.dp else 8.dp
                val endPadding = if (column == 0) 8.dp else 16.dp
                OutfitItem(
                    modifier = Modifier
                        .padding(start = startPadding, end = endPadding, top = 8.dp, bottom = 8.dp),
                    image = outfit.image,
                    text = outfit.name,
                    price = "${stringResource(outfit.currencyRes)} ${outfit.priceAmount}",
                    isFavorite = state.favoriteOutfits.contains(outfit.id),
                    onFavoriteClick = { onEvent(HomeEvent.FavoriteClicked(outfit.id)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}

@Composable
private fun CategoryItem(
    text: String,
    isSelected: Boolean = false,
    horizontalPadding: Dp = 32.dp,
    onCategoryClick: () -> Unit,
) {
    val backgroundColor = if (isSelected) Primary else PrimaryDisabled
    val textColor = if (isSelected) White else OnPrimaryDisabled

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable { onCategoryClick() }
            .padding(horizontal = horizontalPadding, vertical = 16.dp),

        contentAlignment = Alignment.Center) {
        Text(
            text = text,
            color = textColor,
        )
    }
}

@Composable
private fun OutfitItem(
    modifier: Modifier = Modifier,
    image: String,
    text: String,
    price: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .height(210.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .size(36.dp)
                    .clip(RoundedCornerShape(50))
                    .background(White)
                    .align(Alignment.BottomStart)
                    .clickable {
                        onFavoriteClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_heart_filled),
                    contentDescription = null,
                    tint = if (isFavorite) Red else Gray
                )
            }
        }
        Text(
            text = text,
            color = White,
            modifier = Modifier
                .padding(top = 4.dp)
        )
        Text(text = price, color = White)
    }
}


@Preview(showBackground = true)
@Composable
fun OutfitItemPreview() {
    MyApplicationTheme {
        OutfitItem(
            text = "Party",
            image = "true",
            price = "$11.12",
            isFavorite = true,
            onFavoriteClick = {  },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    MyApplicationTheme {
        CategoryItem(
            text = "Party", isSelected = true, onCategoryClick = { })
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleComposablePreview() {
    MyApplicationTheme {
        HomeContent(
            state = HomeState(
                categories = listOf(),
                outfits = listOf(),
            ), onEvent = { })
    }
}