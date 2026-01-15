package com.example.academy_tbc.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.academy_tbc.presentation.theme.MyApplicationTheme

@Composable
fun HomeScreen() {
    HomeContent()
}


@Composable
fun HomeContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "ITS HOME!!!", fontSize = 64.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleComposablePreview() {
    MyApplicationTheme {
        HomeContent()
    }
}