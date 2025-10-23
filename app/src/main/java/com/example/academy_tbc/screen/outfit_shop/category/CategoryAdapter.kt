package com.example.academy_tbc.screen.outfit_shop.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.CategoryButtonItemBinding

class CategoryAdapter(
    private val onCategoryClick: () -> Unit
) :
    ListAdapter<CategoryItem, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtils()) {

    private var currentSelection = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryButtonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    inner class CategoryViewHolder(
        val binding: CategoryButtonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(categoryItem: CategoryItem) = with(binding) {
            btnCategory.text = categoryItem.title

            val backgroundColor: Int = if (currentSelection == bindingAdapterPosition) R.color.primary else R.color.secondary
            val textColor: Int = if (currentSelection == bindingAdapterPosition) R.color.white else R.color.onSecondary

            btnCategory.setBackgroundColor(ContextCompat.getColor(root.context, backgroundColor))
            btnCategory.setTextColor(ContextCompat.getColor(root.context, textColor))
            btnCategory
            btnCategory.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                val oldSelection = currentSelection
                currentSelection = bindingAdapterPosition
//                notifyDataSetChanged()
                notifyItemChanged(oldSelection)
                notifyItemChanged(currentSelection)
            }
        }
    }

}

class CategoryDiffUtils() : DiffUtil.ItemCallback<CategoryItem>() {
    override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
        return oldItem == newItem
    }
}