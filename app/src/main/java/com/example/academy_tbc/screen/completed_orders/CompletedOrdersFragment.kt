package com.example.academy_tbc.screen.completed_orders

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.BottomSheetFragment
import com.example.academy_tbc.OrderStatus
import com.example.academy_tbc.OrdersViewModel
import com.example.academy_tbc.adapters.OrdersAdapter
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentCompletedOrdersBinding
import kotlinx.coroutines.launch

class CompletedOrdersFragment :
    BaseFragment<FragmentCompletedOrdersBinding>(FragmentCompletedOrdersBinding::inflate) {

    private val viewModel: OrdersViewModel by activityViewModels()
    private val adapter by lazy {
        OrdersAdapter { order ->
            viewModel.selectOrder(order)
            BottomSheetFragment().show(parentFragmentManager, ORDER_BOTTOM_SHEET_TAG)
        }
    }

    override fun bind() {
        binding.rvCompletedOrders.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvCompletedOrders.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orders.collect { orders ->
                adapter.submitList(orders.filter { it.status == OrderStatus.COMPLETED })
            }
        }
    }

    companion object {
        const val ORDER_BOTTOM_SHEET_TAG = "order_bottom_sheet_tag"
    }
}