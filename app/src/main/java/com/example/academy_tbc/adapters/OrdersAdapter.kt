package com.example.academy_tbc.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.OrderItem
import com.example.academy_tbc.OrderStatus
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.ItemOrderBinding

class OrdersAdapter(
    val onClick: (OrderItem) -> Unit
) : ListAdapter<OrderItem, OrdersAdapter.OrderViewHolder>(
    OrdersDiffUtils()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): OrderViewHolder {
        return OrderViewHolder(
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: OrderViewHolder, position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class OrderViewHolder(
        val binding: ItemOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderItem) = with(binding) {

            imgProduct.setImageResource(order.image)
            val background = vProductColor.background as GradientDrawable
            background.setColor(ContextCompat.getColor(root.context, order.productColor))
            tvProductName.text = order.title
            tvProductColor.text = order.productColorName
            tvQuantity.text = root.context.getString(R.string.quantity, order.quantity)
            tvStatus.text = order.status.name
            tvPrice.text = root.context.getString(R.string.price, order.price)

            if (order.status == OrderStatus.COMPLETED) {
                btnAction.text = if (order.isReviewed) root.context.getString(R.string.buy_again)
                else root.context.getString(R.string.leave_review)
                btnAction.isEnabled = !order.isReviewed
                btnAction.alpha = if (btnAction.isEnabled) 1f else 0.5f
            } else {
                btnAction.text = root.context.getString(R.string.track_order)
                btnAction.isEnabled = true
                btnAction.alpha = 1f
            }

            btnAction.setOnClickListener {
                onClick(order)
            }
        }
    }
}

class OrdersDiffUtils() : DiffUtil.ItemCallback<OrderItem>() {
    override fun areItemsTheSame(
        oldItem: OrderItem, newItem: OrderItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: OrderItem, newItem: OrderItem
    ): Boolean {
        return oldItem == newItem
    }
}