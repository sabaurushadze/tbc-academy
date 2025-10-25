package com.example.academy_tbc.screen.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.ItemOrderBinding
import com.example.academy_tbc.utils.HelperFunctions
import java.util.UUID


class OrdersAdapter() : ListAdapter<OrderItem, OrdersAdapter.OrderViewHolder>(OrderDiffUtils()) {
    private var listener: (UUID) -> Unit = {}

    fun onClick(listener: (UUID) -> Unit) {
        this.listener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class OrderViewHolder(
        val binding: ItemOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderItem) = with(binding) {
            tvOrderName.text = order.orderName
            tvTrackingNumberCode.text = order.trackingNum
            tvQuantityAmount.text = order.quantity.toString()
            tvSubtotalAmount.text = root.context.getString(R.string.subtotal_amount, order.subTotal)
            tvDeliveryStatus.text = order.status.name
            tvDate.text = HelperFunctions.convertMillisecondsToDate(order.orderDate)
            val statusColor = when (order.status) {
                OrderStatus.PENDING -> root.context.getColor(R.color.pending)
                OrderStatus.DELIVERED -> root.context.getColor(R.color.delivered)
                OrderStatus.CANCELED -> root.context.getColor(R.color.canceled)
            }
            tvDeliveryStatus.setTextColor(statusColor)

            btnDetails.setOnClickListener {
                listener(order.id)
            }
        }
    }
}

class OrderDiffUtils() : DiffUtil.ItemCallback<OrderItem>() {
    override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem == newItem
    }
}