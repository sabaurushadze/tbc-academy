package com.example.academy_tbc.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.user.MessageType
import com.example.academy_tbc.presentation.designsystem.AppButtonFilledIcon
import com.example.academy_tbc.presentation.designsystem.TextInputField
import com.example.academy_tbc.presentation.extension.CollectEvent
import com.example.academy_tbc.presentation.theme.AppColor
import com.example.academy_tbc.presentation.theme.AppDimens
import com.example.academy_tbc.presentation.theme.AppTextStyle
import com.example.academy_tbc.presentation.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
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
        viewModel.onEvent(HomeEvent.GetUsers())
    }
    CollectEvent(
        viewModel.sideEffect
    ) { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                onShowSnackBar(error)
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
            .fillMaxSize()
            .background(AppColor.background)
            .padding(WindowInsets.systemBars.asPaddingValues()),
    ) {
        SearchContent(
            state = state,
            onEvent = onEvent
        )

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = AppDimens.size16),

            verticalArrangement = Arrangement.spacedBy(AppDimens.size20),
            contentPadding = PaddingValues(vertical = AppDimens.size16)
        ) {
            items(state.users) { user ->
                UserItem(
                    owner = user.owner,
                    image = user.image,
                    lastActive = user.lastActive,
                    lastMessage = user.lastMessage,
                    isTyping = user.isTyping,
                    unreadMessages = user.unreadMessages,
                    messageType = user.lastMessageType
                )
            }


        }
    }
}


@Composable
private fun UserItem(
    owner: String,
    image: String?,
    lastActive: String,
    lastMessage: String,
    isTyping: Boolean,
    unreadMessages: Int,
    messageType: MessageType,
) {
    val lastActiveColor = if (unreadMessages == 0) AppColor.neutral1 else AppColor.onBackground

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.size16, vertical = AppDimens.size12),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(AppDimens.size48)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                model = image,
                contentDescription = null,
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder),
                fallback = painterResource(R.drawable.placeholder),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(AppDimens.size16))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = owner,
                    style = AppTextStyle.body16Bold,
                    color = AppColor.onBackground
                )
                Text(
                    text = lastActive,
                    style = AppTextStyle.body12Medium,
                    color = lastActiveColor
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.size8))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                when (messageType) {
                    MessageType.TEXT -> {
                        Text(
                            text = lastMessage,
                            style = AppTextStyle.body12Medium,
                            color = lastActiveColor
                        )
                    }

                    MessageType.VOICE -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.voice),
                                contentDescription = null,
                                modifier = Modifier.size(AppDimens.size16),
                                tint = Color.Unspecified,
                            )

                            Spacer(modifier = Modifier.width(AppDimens.size8))

                            Text(
                                text = stringResource(R.string.sent_a_voice_message),
                                style = AppTextStyle.body12Medium,
                                color = lastActiveColor
                            )
                        }
                        Text(
                            text = lastMessage,
                            style = AppTextStyle.body12Medium,
                            color = lastActiveColor
                        )
                    }

                    MessageType.FILE -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.attach),
                                contentDescription = null,
                                modifier = Modifier.size(AppDimens.size16),
                                tint = Color.Unspecified,
                            )

                            Spacer(modifier = Modifier.width(AppDimens.size8))

                            Text(
                                text = stringResource(R.string.sent_an_attachment),
                                style = AppTextStyle.body12Medium,
                                color = lastActiveColor
                            )
                        }
                    }
                }
                if (isTyping) {
                    Icon(
                        painter = painterResource(id = R.drawable.typing),
                        contentDescription = null,
                        modifier = Modifier.size(AppDimens.size16),
                        tint = AppColor.primary
                    )
                }
                if (unreadMessages > 0) {
                    NumberBadge(unreadMessages)
                }

            }

            Spacer(
                modifier = Modifier
                    .height(AppDimens.size16)
            )

            HorizontalDivider(
                modifier = Modifier
                    .background(color = AppColor.neutral2)
                    .fillMaxWidth()
            )


        }
    }

}


@Composable
fun SearchContent(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = AppDimens.size16)
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextInputField(
            value = state.query,
            onTextChanged = { onEvent(HomeEvent.QueryChanged(it)) },
            modifier = Modifier.weight(1f),
            label = stringResource(R.string.search),
            enabled = !state.isLoading,
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Email,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = null,
                    tint = AppColor.neutral3
                )
            }
        )

        Spacer(modifier = Modifier.width(AppDimens.size20))

        AppButtonFilledIcon(
            modifier = Modifier
                .fillMaxHeight(),
            onClick = { onEvent(HomeEvent.GetUsers(query = state.query)) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = null,
                    modifier = Modifier.height(AppDimens.size24)
                )
            }
        )
    }
}

@Composable
fun NumberBadge(
    number: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(AppDimens.size16)
            .background(
                color = Color(0xFF2ECC71),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = AppColor.onPrimary,
            style = AppTextStyle.body12Medium
        )
    }
}


@Preview(showBackground = true)
@Composable
fun OrderItemPreview() {
    AppTheme {
        UserItem(
            owner = "",
            image = "https://www.alia.ge/wp-content/uploads/2022/09/grisha.jpg",
            lastActive = "2:23pm",
            lastMessage = "",
            isTyping = true,
            unreadMessages = 4,
            messageType = MessageType.FILE
        )
    }
}