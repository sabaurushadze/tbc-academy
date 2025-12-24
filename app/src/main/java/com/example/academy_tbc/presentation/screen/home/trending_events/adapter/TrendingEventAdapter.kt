package com.example.academy_tbc.presentation.screen.home.trending_events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemTrendingEventBinding
import com.example.academy_tbc.presentation.extension.loadImage
import com.example.academy_tbc.presentation.screen.home.trending_events.model.TrendingEventUi

class TrendingEventAdapter(
    val onClick: (Int) -> Unit,
) :
    ListAdapter<TrendingEventUi, TrendingEventAdapter.UserViewHolder>(TrendingEventDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): UserViewHolder {
        return UserViewHolder(
            ItemTrendingEventBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: ItemTrendingEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: TrendingEventUi) = with(binding) {
            ivEvent.loadImage(event.imageUrl)
            tvTitle.text = event.title
            tvEventTime.text = event.date

            root.setOnClickListener {
                onClick(event.id)
            }
        }
    }
}

class TrendingEventDiffUtil : DiffUtil.ItemCallback<TrendingEventUi>() {
    override fun areItemsTheSame(
        oldItem: TrendingEventUi, newItem: TrendingEventUi,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: TrendingEventUi, newItem: TrendingEventUi,
    ): Boolean {
        return oldItem == newItem
    }

}