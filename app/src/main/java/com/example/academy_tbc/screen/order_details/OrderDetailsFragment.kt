package com.example.academy_tbc.screen.order_details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.R
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentOrderDetailsBinding
import com.example.academy_tbc.screen.orders.OrderItem
import com.example.academy_tbc.screen.orders.OrderStatus
import com.example.academy_tbc.screen.orders.OrdersFragment.Companion.BUNDLE_KEY_DELIVERY_STATUS
import com.example.academy_tbc.screen.orders.OrdersFragment.Companion.REQ_KEY_DELIVERY_STATUS
import com.example.academy_tbc.utils.HelperFunctions

class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>(
    FragmentOrderDetailsBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQ_KEY_DELIVERY_STATUS) { requestKey, bundle ->
            val order = bundle.getParcelable<OrderItem>(BUNDLE_KEY_DELIVERY_STATUS)
            order?.let {
                if (order.status != OrderStatus.PENDING) {
                    binding.btbCanceled.visibility = View.GONE
                    binding.btnDelivered.visibility = View.GONE
                }
                setOrderItems(order)
                onDeliveredClick(order)
                onCanceledClick(order)
            }
        }
    }

    private fun setOrderItems(orderItem: OrderItem) = with(binding) {
        tvOrderNameDetails.text = orderItem.orderName
        tvOrderDateDetails.text = HelperFunctions.convertMillisecondsToDate(orderItem.orderDate)
        tvTrackingNumberDetails.text = orderItem.trackingNum
        tvQuantityDetails.text = orderItem.quantity.toString()
        tvStatusDetails.text = orderItem.status.name
        tvSubtotalDetails.text = getString(R.string.subtotal_price, orderItem.subTotal.toString())

        val totalPrice = orderItem.subTotal * orderItem.quantity
        tvTotalDetails.text = getString(R.string.total_price, totalPrice)
    }

    private fun onDeliveredClick(orderItem: OrderItem) {
        binding.btnDelivered.setOnClickListener {
            val deliveredOrderStatusItem = orderItem.copy(
                status = OrderStatus.DELIVERED
            )
            setFragmentResult(
                REQ_KEY_DELIVERY_STATUS_CHANGED_TO_DELIVERED,
                bundleOf(BUNDLE_KEY_DELIVERY_STATUS_CHANGED_TO_DELIVERED to deliveredOrderStatusItem)
            )
            findNavController().popBackStack()

        }
    }

    private fun onCanceledClick(orderItem: OrderItem) {
        binding.btbCanceled.setOnClickListener {
            val canceledOrderStatusItem = orderItem.copy(
                status = OrderStatus.CANCELED
            )
            setFragmentResult(
                REQ_KEY_DELIVERY_STATUS_CHANGED_TO_CANCELED,
                bundleOf(BUNDLE_KEY_DELIVERY_STATUS_CHANGED_TO_CANCELED to canceledOrderStatusItem)
            )
            findNavController().popBackStack()

        }
    }

    companion object {
        const val REQ_KEY_DELIVERY_STATUS_CHANGED_TO_CANCELED =
            "req_key_delivery_status_changed_to_canceled"
        const val BUNDLE_KEY_DELIVERY_STATUS_CHANGED_TO_CANCELED =
            "bundle_key_delivery_status_changed_to_canceled"

        const val REQ_KEY_DELIVERY_STATUS_CHANGED_TO_DELIVERED =
            "req_key_delivery_status_changed_to_delivered"
        const val BUNDLE_KEY_DELIVERY_STATUS_CHANGED_TO_DELIVERED =
            "bundle_key_delivery_status_changed_to_delivered"
    }
}