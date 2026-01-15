package com.example.academy_tbc.presentation.screen.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.academy_tbc.R
import com.example.academy_tbc.presentation.designsystem.AppButton
import com.example.academy_tbc.presentation.theme.Black
import com.example.academy_tbc.presentation.theme.White

@Composable
fun OnboardingScreen(
    navigateToLogin: () -> Unit,
    navigateToRegister: () -> Unit,
) {
    OnboardingContent(
        onLogInClick = navigateToLogin,
        onRegisterClick = navigateToRegister
    )
}

@Composable
fun OnboardingContent(
    onLogInClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Image(
                painter = painterResource(R.drawable.start_screen),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
            Image(
                painter = painterResource(R.drawable.icon_company),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AppButton(
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                text = stringResource(R.string.log_in),
                border = BorderStroke(1.dp, Black),
                textColor = Black,
                textSize = 14.sp
            ) {
                onLogInClick()
            }

            Spacer(modifier = Modifier.width(8.dp))

            AppButton(
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                text = stringResource(R.string.register),
                buttonColor = Black,
                textColor = White,
                textSize = 14.sp
            ) {
                onRegisterClick()
            }

        }

    }
}

@Composable
@Preview
fun OnboardingContentPreview() {
    OnboardingContent(
        onRegisterClick = {},
        onLogInClick = {}
    )
}