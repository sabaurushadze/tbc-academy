package com.example.academy_tbc

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentOrdersBinding
import com.example.academy_tbc.screen.active_orders.ActiveOrdersFragment
import com.example.academy_tbc.screen.completed_orders.CompletedOrdersFragment
import com.google.android.material.tabs.TabLayoutMediator

class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {
    override fun bind() {
        binding.viewPager.adapter = OrdersPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) OrderStatus.ACTIVE.name else OrderStatus.COMPLETED.name
        }.attach()
    }

    inner class OrdersPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount() = 2
        override fun createFragment(position: Int) =
            if (position == 0) ActiveOrdersFragment() else CompletedOrdersFragment()
    }
}