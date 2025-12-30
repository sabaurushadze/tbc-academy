package com.example.academy_tbc.presentation.screen.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemDotBinding
import com.example.academy_tbc.presentation.screen.categories.model.DepthDot


class CategoryDepthAdapter :
    ListAdapter<DepthDot, CategoryDepthAdapter.DepthViewHolder>(DepthDiffUtil()) {

    class DepthViewHolder(
        binding: ItemDotBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DepthDot) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepthViewHolder {
        val binding =
            ItemDotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DepthViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepthViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DepthDiffUtil : DiffUtil.ItemCallback<DepthDot>() {
    override fun areItemsTheSame(oldItem: DepthDot, newItem: DepthDot): Boolean =
        oldItem.index == newItem.index

    override fun areContentsTheSame(oldItem: DepthDot, newItem: DepthDot): Boolean =
        oldItem == newItem
}

