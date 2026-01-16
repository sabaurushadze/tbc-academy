package com.example.academy_tbc.presentation.screen.register_username

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.academy_tbc.R
import com.example.academy_tbc.presentation.designsystem.AppButton
import com.example.academy_tbc.presentation.theme.Black
import com.example.academy_tbc.presentation.theme.MyApplicationTheme
import com.example.academy_tbc.presentation.theme.White

@Composable
fun RegisterUsernameScreen(
    viewModel: RegisterUsernameViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit = {},
    onShowSnackBar: (String) -> Unit,
) {
    val context = LocalResources.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterUsernameContent(
        state = state,
        onEvent = viewModel::onEvent,
        onBackClick = { navigateBack() }
    )

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                RegisterUsernameSideEffect.NavigateToHome -> {
                    navigateToHome()
                }

                is RegisterUsernameSideEffect.ShowSnackBar -> {
                    val error = context.getString(sideEffect.errorRes)
                    onShowSnackBar(error)
                }
            }
        }
    }
}


@Composable
fun RegisterUsernameContent(
    state: RegisterUsernameState,
    onEvent: (RegisterUsernameEvent) -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackClick() },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back_24),
                    contentDescription = null
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                modifier = Modifier.padding(
                    bottom = 32.dp,
                    top = 24.dp
                ),
                text = stringResource(R.string.register_lowercase),
                fontSize = 32.sp
            )

            OutlinedTextField(
                value = state.username,
                onValueChange = { onEvent(RegisterUsernameEvent.UsernameChanged(it)) },
                label = { Text(stringResource(R.string.username)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Black,
                    focusedTextColor = Black,
                    focusedLabelColor = Black,
                    focusedLeadingIconColor = Black,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                text = "SIGN UP",
                buttonColor = Black,
                textColor = White,
                textSize = 14.sp,
                onClick = {
                    onEvent(RegisterUsernameEvent.Register)
                }
            )
            Spacer(modifier = Modifier.height(32.dp))

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Black
                )
            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun SimpleComposablePreview() {
    MyApplicationTheme {
        RegisterUsernameContent(
            state = RegisterUsernameState(
                isLoading = true,
            ),
            onEvent = {},
            onBackClick = {}
        )
    }
}