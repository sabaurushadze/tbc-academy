package com.example.academy_tbc.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.order.OrderStatus
import com.example.academy_tbc.presentation.designsystem.AppButtonOutlined
import com.example.academy_tbc.presentation.extension.CollectEvent
import com.example.academy_tbc.presentation.screen.home.orders.component.OrderDetailsSheet
import com.example.academy_tbc.presentation.screen.home.orders.enums.OrdersTab
import com.example.academy_tbc.presentation.theme.AppColor
import com.example.academy_tbc.presentation.theme.AppDimens
import com.example.academy_tbc.presentation.theme.AppRadius
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

    val bottomSheetState = rememberModalBottomSheetState()

    state.selectedOrder?.let {
        ModalBottomSheet(
            onDismissRequest = { viewModel.onEvent(HomeEvent.UnselectOrder) },
            sheetState = bottomSheetState
        ) {
            state.selectedOrder?.let { order ->
                OrderDetailsSheet(
                    order = order,
                    onDeliver = {
                        viewModel.onEvent(HomeEvent.UpdateOrder(order.id, OrderStatus.DELIVERED))
                        viewModel.onEvent(HomeEvent.UnselectOrder)
                    },
                    onCancel = {
                        viewModel.onEvent(HomeEvent.UpdateOrder(order.id, OrderStatus.CANCELED))
                        viewModel.onEvent(HomeEvent.UnselectOrder)
                    }
                )
            }
        }
    }

    HomeContent(
        state = state, onEvent = viewModel::onEvent
    )

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeEvent.GetOrders)
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
        HomeTabs(
            tabs = OrdersTab.entries.toList(),
            selectedTab = state.selectedTab,
            onTabSelected = { onEvent(HomeEvent.TabSelected(it)) }
        )

        Spacer(modifier = Modifier.height(AppDimens.size8))

        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { onEvent(HomeEvent.GetOrders) }
        ) {
            if (state.isLoading && state.orders.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = AppColor.primary
                    )
                }
            } else if (state.orders.isEmpty()) {
                EmptyOrdersScreen(state.selectedTab)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = AppDimens.size16),

                    verticalArrangement = Arrangement.spacedBy(AppDimens.size20),
                    contentPadding = PaddingValues(vertical = AppDimens.size16)
                ) {
                    items(state.orders) { order ->
                        OrderItem(
                            orderName = order.orderName,
                            quantity = order.quantity,
                            statusTextRes = order.statusTextRes,
                            statusType = order.status,
                            trackingNumber = order.trackingNumber,
                            deliveryDate = order.deliveryDate,
                            subtotal = order.subtotal,
                            onDetailsClick = {
                                onEvent(HomeEvent.SelectOrder(order))
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(AppDimens.size32))

    }
}

@Composable
fun HomeTabs(
    tabs: List<OrdersTab>,
    selectedTab: OrdersTab,
    onTabSelected: (OrdersTab) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppDimens.size16),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.size16),

        ) {
        Spacer(modifier = Modifier.weight(1f))

        tabs.forEach { tab ->
            val isSelected = tab == selectedTab
            Text(
                text = stringResource(tab.titleRes),
                color = if (isSelected) AppColor.onPrimary else AppColor.onBackground,
                style = AppTextStyle.body14Medium,
                modifier = Modifier
                    .background(
                        color = if (isSelected) AppColor.neutral2 else AppColor.background,
                        shape = AppRadius.radius16
                    )
                    .padding(vertical = AppDimens.size8, horizontal = AppDimens.size16)
                    .clickable { onTabSelected(tab) }
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun OrderItem(
    orderName: String,
    quantity: String,
    statusTextRes: Int,
    statusType: OrderStatus,
    trackingNumber: String,
    deliveryDate: String,
    subtotal: String,
    onDetailsClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = AppRadius.radius10,
        colors = CardDefaults.cardColors(
            containerColor = AppColor.background
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.size8)
    ) {
        Column(modifier = Modifier.padding(AppDimens.size16)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = orderName,
                    style = AppTextStyle.title18Bold
                )
                Text(
                    text = deliveryDate,
                    style = AppTextStyle.body14Medium,
                    color = AppColor.neutral1
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.size24))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.tracking_number),
                    style = AppTextStyle.body14Medium,
                    color = AppColor.neutral1
                )
                Spacer(modifier = Modifier.width(AppDimens.size12))

                Text(
                    text = trackingNumber,
                    style = AppTextStyle.body14Medium
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.size24))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quantity:",
                        style = AppTextStyle.body14Medium,
                        color = AppColor.neutral1
                    )
                    Spacer(modifier = Modifier.width(AppDimens.size8))

                    Text(
                        text = quantity,
                        style = AppTextStyle.body14Medium
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.subtotal_amount),
                        style = AppTextStyle.body14Medium,
                        color = AppColor.neutral1
                    )
                    Spacer(modifier = Modifier.width(AppDimens.size4))

                    Text(
                        text = subtotal,
                        style = AppTextStyle.body16Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.size24))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(statusTextRes),
                    style = AppTextStyle.body14Medium,
                    color = statusColor(statusType)
                )
                Spacer(modifier = Modifier.width(AppDimens.size12))

                AppButtonOutlined(
                    text = stringResource(R.string.details),
                    shape = AppRadius.radius16,
                    textStyle = AppTextStyle.body14Medium,
                    onClick = { onDetailsClick() }
                )
            }
        }
    }

}


@Composable
fun EmptyOrdersScreen(tab: OrdersTab) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppDimens.size16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = when (tab) {
                OrdersTab.PENDING -> stringResource(R.string.no_pending_orders)
                OrdersTab.DELIVERED -> stringResource(R.string.no_delivered_orders)
                OrdersTab.CANCELED -> stringResource(R.string.no_canceled_orders)
            },
            color = AppColor.onBackground,
            style = AppTextStyle.title18Bold
        )
    }
}

@Composable
fun statusColor(status: OrderStatus): Color {
    return when (status) {
        OrderStatus.PENDING -> AppColor.warning
        OrderStatus.DELIVERED -> AppColor.success
        OrderStatus.CANCELED -> AppColor.error
    }
}


@Preview(showBackground = true)
@Composable
fun OrderItemPreview() {
    AppTheme {
        OrderItem(
            orderName = "Order #1524",
            quantity = "2",
            statusTextRes = R.string.order_status_pending,
            deliveryDate = "12/05/2021",
            subtotal = "$230",
            trackingNumber = "IK1230178231",
            statusType = OrderStatus.PENDING,
            onDetailsClick = {}
        )
    }
}