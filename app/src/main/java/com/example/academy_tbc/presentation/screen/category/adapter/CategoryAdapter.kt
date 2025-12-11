package com.example.academy_tbc.presentation.screen.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemCategoryBinding
import com.example.academy_tbc.presentation.extension.loadImage
import com.example.academy_tbc.presentation.screen.category.model.CategoryUi

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
        fun bind(pcPartUi: CategoryUi) = with(binding) {
            tvCategory.text = root.context.getString(pcPartUi.categoryRes)
            ivCategory.loadImage(pcPartUi.image)

            binding.root.setOnClickListener {
                onClick(pcPartUi)
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