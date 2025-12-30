package com.example.academy_tbc.presentation.screen.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemCategoryBinding
import com.example.academy_tbc.presentation.screen.categories.model.DepthDot
import com.example.academy_tbc.presentation.screen.categories.model.GetEquipment

class CategoryAdapter() :
    ListAdapter<GetEquipment, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtil()) {

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val depthAdapter by lazy { CategoryDepthAdapter() }

        init {
            binding.rvDepth.apply {
                itemAnimator = null
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = depthAdapter
            }
        }

        fun bind(item: GetEquipment) = with(binding) {
            tvName.text = item.name

            val depthDots = List(item.depth.coerceAtMost(4)) {
                DepthDot(index = it)
            }

            depthAdapter.submitList(depthDots)
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


}

class CategoryDiffUtil : DiffUtil.ItemCallback<GetEquipment>() {
    override fun areItemsTheSame(
        oldItem: GetEquipment, newItem: GetEquipment,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GetEquipment, newItem: GetEquipment,
    ): Boolean {
        return oldItem == newItem
    }

}