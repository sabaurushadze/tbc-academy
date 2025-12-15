package com.example.academy_tbc.presentation.screen.part_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemPartDetailBinding
import com.example.academy_tbc.presentation.extension.loadImage
import com.example.academy_tbc.presentation.screen.part_detail.model.ImageUi

class PartDetailsAdapter() :
    ListAdapter<ImageUi, PartDetailsAdapter.PartDetailViewHolder>(PartDetailDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): PartDetailViewHolder {
        return PartDetailViewHolder(
            ItemPartDetailBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PartDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PartDetailViewHolder(private val binding: ItemPartDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUi: ImageUi) = with(binding) {
            ivPartDetail.loadImage(imageUi.url)


        }
    }
}

class PartDetailDiffUtil : DiffUtil.ItemCallback<ImageUi>() {
    override fun areItemsTheSame(
        oldItem: ImageUi, newItem: ImageUi,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ImageUi, newItem: ImageUi,
    ): Boolean {
        return oldItem == newItem
    }

}