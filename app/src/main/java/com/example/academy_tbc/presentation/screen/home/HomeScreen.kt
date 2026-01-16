package com.example.academy_tbc.presentation.screen.home

import android.util.Log.d
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.academy_tbc.presentation.theme.MyApplicationTheme
import com.example.academy_tbc.presentation.theme.OnPrimaryDisabled
import com.example.academy_tbc.presentation.theme.Primary
import com.example.academy_tbc.presentation.theme.PrimaryDisabled
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
    Column(
        modifier = Modifier
            .padding(WindowInsets.safeDrawing.asPaddingValues())
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(state.categories) { index, category ->
                val horizontalPadding = if (index == 0) 20.dp else 32.dp

                CategoryItem(
                    horizontalPadding = horizontalPadding,
                    text = category.name,
                    isSelected = state.selectedCategoryId == category.id,
                    onCategoryClick = { onEvent(HomeEvent.CategoryClicked(category.id)) })
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(state.outfits) { outfit ->
                OutfitItem(
                    image = outfit.image,
                    text = outfit.name,
                    price = "${stringResource(outfit.currencyRes)} ${outfit.priceAmount}"
                )
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
    image: String,
    text: String,
    price: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp))
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(text = text)
        Text(text = price)
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