package com.example.academy_tbc.presentation.screen.home.category.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.ItemCategoryBinding
import com.example.academy_tbc.presentation.screen.home.category.model.CategoryUi

class CategoryAdapter(
    val onClick: (CategoryUi) -> Unit,
) :
    ListAdapter<CategoryUi, CategoryAdapter.UserViewHolder>(CategoryDiffUtil()) {
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
            tvCategory.text = root.context.getString(category.categoryRes)

            if (category.selected) {
                tvCategory.backgroundTintList = ColorStateList.valueOf(root.context.getColor(R.color.primary))
                tvCategory.setTextColor(root.context.getColor(R.color.white))
            } else {
                tvCategory.backgroundTintList = ColorStateList.valueOf(root.context.getColor(R.color.white))
                tvCategory.setTextColor(root.context.getColor(R.color.black))
            }
            binding.tvCategory.setOnClickListener {
                onClick(category)
            }
        }
    }
}

class CategoryDiffUtil : DiffUtil.ItemCallback<CategoryUi>() {
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