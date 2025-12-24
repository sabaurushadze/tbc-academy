package com.example.academy_tbc.presentation.screen.home.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemCategoryBinding
import com.example.academy_tbc.presentation.screen.home.categories.model.CategoryUi

class CategoryAdapter(
    val onClick: (Int) -> Unit,
) :
    ListAdapter<CategoryUi, CategoryAdapter.UserViewHolder>(EventDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): UserViewHolder {
        return UserViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryUi) = with(binding) {
            tvTitle.text = category.title
            ivCategory.setImageResource(category.icon)
            tvEventCount.text = "${category.totalEvents} events"

            root.setOnClickListener {
                onClick(category.id)
            }
        }
    }
}

class EventDiffUtil : DiffUtil.ItemCallback<CategoryUi>() {
    override fun areItemsTheSame(
        oldItem: CategoryUi, newItem: CategoryUi,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CategoryUi, newItem: CategoryUi,
    ): Boolean {
        return oldItem == newItem
    }

}