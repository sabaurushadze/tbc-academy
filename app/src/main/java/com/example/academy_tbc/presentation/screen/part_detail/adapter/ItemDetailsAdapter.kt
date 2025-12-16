package com.example.academy_tbc.presentation.screen.part_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemDetailRowBinding
import com.example.academy_tbc.presentation.screen.part_detail.model.ItemDetailUi

class ItemDetailsAdapter() :
    ListAdapter<ItemDetailUi, ItemDetailsAdapter.ItemDetailViewHolder>(ItemDetailDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): ItemDetailViewHolder {
        return ItemDetailViewHolder(
            ItemDetailRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemDetailViewHolder(private val binding: ItemDetailRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemDetailUi) = with(binding) {
            tvKey.text = item.title
            tvValue.text = item.value
        }
    }
}

class ItemDetailDiffUtil : DiffUtil.ItemCallback<ItemDetailUi>() {
    override fun areItemsTheSame(
        oldItem: ItemDetailUi, newItem: ItemDetailUi,
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ItemDetailUi, newItem: ItemDetailUi,
    ): Boolean {
        return oldItem == newItem
    }

}