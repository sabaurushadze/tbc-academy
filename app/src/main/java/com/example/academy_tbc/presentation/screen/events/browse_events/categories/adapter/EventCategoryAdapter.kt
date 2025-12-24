package com.example.academy_tbc.presentation.screen.events.browse_events.categories.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.ItemHeaderCategoryBinding
import com.example.academy_tbc.presentation.screen.events.browse_events.categories.model.EventCategoryUi

class EventCategoryAdapter(
    val onClick: (Int) -> Unit,
) :
    ListAdapter<EventCategoryUi, EventCategoryAdapter.UserViewHolder>(EventCategoryDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): UserViewHolder {
        return UserViewHolder(
            ItemHeaderCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: ItemHeaderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: EventCategoryUi) = with(binding) {
            tvCategory.text = category.title

            if (category.selected) {
                tvCategory.backgroundTintList =
                    ColorStateList.valueOf(root.context.getColor(R.color.primary))
                tvCategory.setTextColor(root.context.getColor(R.color.white))
            } else {
                tvCategory.backgroundTintList =
                    ColorStateList.valueOf(root.context.getColor(R.color.light_gray2))
                tvCategory.setTextColor(root.context.getColor(R.color.gray3))
            }

            tvCategory.setOnClickListener {
                onClick(category.id)
            }
        }
    }
}

class EventCategoryDiffUtil : DiffUtil.ItemCallback<EventCategoryUi>() {
    override fun areItemsTheSame(
        oldItem: EventCategoryUi, newItem: EventCategoryUi,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: EventCategoryUi, newItem: EventCategoryUi,
    ): Boolean {
        return oldItem == newItem
    }

}