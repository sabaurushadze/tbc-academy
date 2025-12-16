package com.example.academy_tbc.presentation.screen.home.adapter.paging

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.ItemPcPartBinding
import com.example.academy_tbc.presentation.extension.loadImage
import com.example.academy_tbc.presentation.screen.home.model.PcPartUi

class PcPartsPagingAdapter(
    val onClick: (PcPartUi) -> Unit
) : PagingDataAdapter<PcPartUi, PcPartsPagingAdapter.UserViewHolder>(PcPartsDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): UserViewHolder {
        return UserViewHolder(
            ItemPcPartBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class UserViewHolder(private val binding: ItemPcPartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pcPartUi: PcPartUi) = with(binding) {
            ivPcPart.loadImage(
                url = pcPartUi.images.first(),
                placeholderRes = R.drawable.generic_placeholder
            )
            tvTitle.text = pcPartUi.title
            tvPrice.text = root.context.getString(R.string.price, pcPartUi.price)
            tvPriceBefore.isVisible = pcPartUi.hasDiscount
            tvCondition.text = root.context.getString(pcPartUi.conditionTextRes)
            if (pcPartUi.hasDiscount) {
                tvPriceBefore.paintFlags = tvPriceBefore.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                tvPriceBefore.text = root.context.getString(R.string.price_before, pcPartUi.priceBefore)
            }

            binding.root.setOnClickListener {
                onClick(pcPartUi)
            }
        }
    }
}

class PcPartsDiffUtil : DiffUtil.ItemCallback<PcPartUi>() {
    override fun areItemsTheSame(
        oldItem: PcPartUi, newItem: PcPartUi,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PcPartUi, newItem: PcPartUi,
    ): Boolean {
        return oldItem == newItem
    }

}
