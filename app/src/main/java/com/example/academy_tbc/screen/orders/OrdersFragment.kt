package com.example.academy_tbc.screen.orders

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentOrdersBinding
import com.example.academy_tbc.screen.order_details.OrderDetailsFragment.Companion.BUNDLE_KEY_DELIVERY_STATUS_CHANGED_TO_CANCELED
import com.example.academy_tbc.screen.order_details.OrderDetailsFragment.Companion.BUNDLE_KEY_DELIVERY_STATUS_CHANGED_TO_DELIVERED
import com.example.academy_tbc.screen.order_details.OrderDetailsFragment.Companion.REQ_KEY_DELIVERY_STATUS_CHANGED_TO_CANCELED
import com.example.academy_tbc.screen.order_details.OrderDetailsFragment.Companion.REQ_KEY_DELIVERY_STATUS_CHANGED_TO_DELIVERED
import java.util.UUID

class OrdersFragment() : BaseFragment<FragmentOrdersBinding>(
    FragmentOrdersBinding::inflate
) {
    private val orderStatusList = mutableListOf(
        OrderStatusItem(
            id = UUID.randomUUID(), orderStatus = OrderStatus.PENDING
        ),
        OrderStatusItem(
            id = UUID.randomUUID(), orderStatus = OrderStatus.DELIVERED
        ),
        OrderStatusItem(
            id = UUID.randomUUID(), orderStatus = OrderStatus.CANCELED
        ),
    )
    private val ordersList = mutableListOf(
        OrderItem(
            id = UUID.randomUUID(),
            orderDate = 1620849600000,
            orderName = "Order #4321",
            trackingNum = "IK981232341",
            quantity = 10,
            subTotal = 333,
            status = OrderStatus.PENDING,
        ), OrderItem(
            id = UUID.randomUUID(),
            orderDate = 1620849600000,
            orderName = "Order #5531",
            trackingNum = "IK987362340",
            quantity = 5,
            subTotal = 111,
            status = OrderStatus.PENDING,
        ), OrderItem(
            id = UUID.randomUUID(),
            orderDate = 1620849600000,
            orderName = "Order #9853",
            trackingNum = "IK987362342",
            quantity = 5,
            subTotal = 777,
            status = OrderStatus.PENDING,
        ), OrderItem(
            id = UUID.randomUUID(),
            orderDate = 1620849600000,
            orderName = "Order #0001",
            trackingNum = "IK987362441",
            quantity = 125,
            subTotal = 123,
            status = OrderStatus.PENDING,
        ), OrderItem(
            id = UUID.randomUUID(),
            orderDate = 1620849600000,
            orderName = "Order #9482",
            trackingNum = "IK327362341",
            quantity = 2,
            subTotal = 2,
            status = OrderStatus.PENDING,
        ), OrderItem(
            id = UUID.randomUUID(),
            orderDate = 1620849600000,
            orderName = "Order #6654",
            trackingNum = "IK987312341",
            quantity = 1000,
            subTotal = 50,
            status = OrderStatus.PENDING,
        )
    )

    private val orderStatusAdapter by lazy {
        OrderStatusAdapter()
    }

    private val ordersAdapter by lazy {
        OrdersAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQ_KEY_DELIVERY_STATUS_CHANGED_TO_DELIVERED) { requestKey, bundle ->
            val orderItemDelivered =
                bundle.getParcelable<OrderItem>(BUNDLE_KEY_DELIVERY_STATUS_CHANGED_TO_DELIVERED)
            orderItemDelivered?.let { updatedItem ->
                updateOrder(updatedItem)
            }
        }

        setFragmentResultListener(REQ_KEY_DELIVERY_STATUS_CHANGED_TO_CANCELED) { _, bundle ->
            val orderItemCanceled =
                bundle.getParcelable<OrderItem>(BUNDLE_KEY_DELIVERY_STATUS_CHANGED_TO_CANCELED)
            orderItemCanceled?.let { updatedItem ->
                updateOrder(updatedItem)
            }
        }
    }


    override fun bind() {
        ensureActiveItem()
        setupOrderStatusRecyclerView()
        setupOrdersRecyclerView()
        updateFilteredOrders()
    }

    private fun setupOrderStatusRecyclerView() = with(binding.rvOrderStatus) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = orderStatusAdapter

        orderStatusAdapter.submitList(orderStatusList.toList())

        orderStatusAdapter.onClick { clickedId ->
            orderStatusList.replaceAll { it.copy(isActivated = it.id == clickedId) }
            orderStatusAdapter.submitList(orderStatusList.toList())
            updateFilteredOrders()
        }
    }

    private fun setupOrdersRecyclerView() = with(binding.rvOrders) {
        layoutManager = LinearLayoutManager(context)
        adapter = ordersAdapter

        ordersAdapter.onClick { clickedId ->
            val orderItem = ordersList.find { it.id == clickedId }
            setFragmentResult(
                REQ_KEY_DELIVERY_STATUS, bundleOf(BUNDLE_KEY_DELIVERY_STATUS to orderItem)
            )
            findNavController().navigate(OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment())
        }
    }

    private fun updateOrder(updatedItem: OrderItem) {
        val index = ordersList.indexOfFirst { it.id == updatedItem.id }
        if (index != -1) {
            ordersList[index] = updatedItem
            updateFilteredOrders()
        }

        val activeStatus = orderStatusList.find { it.isActivated }?.orderStatus
        val filteredOrders = activeStatus?.let { status ->
            ordersList.filter { it.status == status }
        } ?: ordersList.toList()

        ordersAdapter.submitList(filteredOrders.toList())
    }

    private fun updateFilteredOrders() {
        val activeStatus = orderStatusList.find { it.isActivated }?.orderStatus
        val filteredOrders = activeStatus?.let { status ->
            ordersList.filter { it.status == status }
        } ?: ordersList
        ordersAdapter.submitList(filteredOrders)
    }

    private fun ensureActiveItem() {
        if (orderStatusList.none { it.isActivated } && orderStatusList.isNotEmpty()) {
            orderStatusList[0] = orderStatusList[0].copy(isActivated = true)
        }
    }

    companion object {
        const val REQ_KEY_DELIVERY_STATUS = "req_key_delivery_status"
        const val BUNDLE_KEY_DELIVERY_STATUS = "bundle_key_delivery_status"
    }
}