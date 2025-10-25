package com.example.academy_tbc.screen.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.ItemOrderStatusBinding
import java.util.UUID

class OrderStatusAdapter() :
    ListAdapter<OrderStatusItem, OrderStatusAdapter.OrderStatusViewHolder>(OrderStatusDiffUtils()) {
    private var listener: (UUID) -> Unit = {}

    fun onClick(listener: (UUID) -> Unit) {
        this.listener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderStatusViewHolder {
        return OrderStatusViewHolder(
            ItemOrderStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: OrderStatusViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class OrderStatusViewHolder(
        val binding: ItemOrderStatusBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderStatus: OrderStatusItem) = with(binding) {
            btnOrderStatus.text =
                orderStatus.orderStatus.name.lowercase().replaceFirstChar { it.uppercaseChar() }

            if (orderStatus.isActivated) {
                btnOrderStatus.setTextColor(root.context.getColor(R.color.white))
                btnOrderStatus.backgroundTintList =
                    root.context.getColorStateList(R.color.container)
            } else {
                btnOrderStatus.setTextColor(root.context.getColor(R.color.black))
                btnOrderStatus.backgroundTintList =
                    root.context.getColorStateList(android.R.color.transparent)
            }

            btnOrderStatus.setOnClickListener {
                listener(orderStatus.id)
            }
        }
    }
}

class OrderStatusDiffUtils() : DiffUtil.ItemCallback<OrderStatusItem>() {
    override fun areItemsTheSame(oldItem: OrderStatusItem, newItem: OrderStatusItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OrderStatusItem, newItem: OrderStatusItem): Boolean {
        return oldItem == newItem
    }
}