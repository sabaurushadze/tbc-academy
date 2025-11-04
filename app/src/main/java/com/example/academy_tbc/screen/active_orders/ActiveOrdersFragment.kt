package com.example.academy_tbc.screen.active_orders

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.OrderStatus
import com.example.academy_tbc.OrdersViewModel
import com.example.academy_tbc.adapters.OrdersAdapter
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentActiveOrdersBinding
import kotlinx.coroutines.launch

class ActiveOrdersFragment :
    BaseFragment<FragmentActiveOrdersBinding>(FragmentActiveOrdersBinding::inflate) {

    private val viewModel: OrdersViewModel by activityViewModels()
    private val adapter by lazy { OrdersAdapter {} }


    override fun bind() {
        binding.rvActiveOrders.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvActiveOrders.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orders.collect { orders ->
                adapter.submitList(orders.filter { it.status == OrderStatus.ACTIVE })
            }
        }
    }
}