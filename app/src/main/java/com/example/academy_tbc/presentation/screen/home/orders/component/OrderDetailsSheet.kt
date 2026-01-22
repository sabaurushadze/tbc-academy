package com.example.academy_tbc.presentation.screen.home.orders.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.order.OrderStatus
import com.example.academy_tbc.presentation.designsystem.AppButtonOutlined
import com.example.academy_tbc.presentation.screen.home.orders.model.UiBorderDelayDetails
import com.example.academy_tbc.presentation.screen.home.orders.model.UiBulkOrderDetails
import com.example.academy_tbc.presentation.screen.home.orders.model.UiHighValueOrderDetails
import com.example.academy_tbc.presentation.screen.home.orders.model.UiInTransitDetails
import com.example.academy_tbc.presentation.screen.home.orders.model.UiOrder
import com.example.academy_tbc.presentation.theme.AppDimens
import com.example.academy_tbc.presentation.theme.AppTextStyle

@Composable
fun OrderDetailsSheet(
    order: UiOrder,
    onDeliver: () -> Unit,
    onCancel: () -> Unit,
) {
    Column(modifier = Modifier.padding(AppDimens.size16)) {
        Text(order.orderName, style = AppTextStyle.title18Bold)
        Spacer(modifier = Modifier.height(AppDimens.size16))
        Text(stringResource(R.string.tracking, order.trackingNumber))
        Text(stringResource(R.string.quantity, order.quantity))
        Text(stringResource(R.string.subtotal, order.subtotal))
        Text(stringResource(R.string.delivery, order.deliveryDate))

        Spacer(Modifier.height(AppDimens.size16))

        when (val details = order.details) {
            is UiBorderDelayDetails -> {
                Text(stringResource(R.string.reason, details.reason))
                Text(stringResource(R.string.border_country, details.borderCountry))
                Text(stringResource(R.string.estimated_delay_days, details.estimatedDelayDays))
            }

            is UiBulkOrderDetails -> {
                Text(stringResource(R.string.warehouse, details.warehouseId))
                Text(stringResource(R.string.pallets, details.palletCount))
                Text(stringResource(R.string.instructions, details.handlingInstructions))
            }

            is UiHighValueOrderDetails -> {
                Text(stringResource(R.string.insured, details.insuredAmount))
                Text(stringResource(R.string.requires_signature, details.requiresSignature))
                Text(stringResource(R.string.fragile, details.fragile))
            }

            is UiInTransitDetails -> {
                Text(stringResource(R.string.current_city, details.currentCity))
                Text(stringResource(R.string.next_checkpoint, details.nextCheckpoint))
                Text(stringResource(R.string.progress, details.progressPercent))
            }
        }

        Spacer(Modifier.height(AppDimens.size24))
        Row(horizontalArrangement = Arrangement.spacedBy(AppDimens.size24)) {
            if (order.status == OrderStatus.PENDING) {
                AppButtonOutlined(text = stringResource(R.string.deliver), onClick = onDeliver)
                AppButtonOutlined(text = stringResource(R.string.cancel), onClick = onCancel)
            }
        }
    }
}