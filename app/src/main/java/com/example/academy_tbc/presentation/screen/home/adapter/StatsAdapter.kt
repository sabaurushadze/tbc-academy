package com.example.academy_tbc.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemStatsBinding
import com.example.academy_tbc.presentation.extension.loadImage
import com.example.academy_tbc.presentation.screen.home.StatsUi

class StatsAdapter() :
    ListAdapter<StatsUi, StatsAdapter.UserViewHolder>(UserDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): UserViewHolder {
        return UserViewHolder(
            ItemStatsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: ItemStatsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StatsUi) = with(binding) {
            tvLocation.text = item.location
            tvAltitude.text = item.altitudeM.toString()
            tvMainDescription.text = item.title
            ratingBar.rating = (item.stars ?: 0).toFloat()
            image.loadImage(item.image)
        }
    }
}

class UserDiffUtil : DiffUtil.ItemCallback<StatsUi>() {
    override fun areItemsTheSame(
        oldItem: StatsUi, newItem: StatsUi
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: StatsUi, newItem: StatsUi
    ): Boolean {
        return oldItem == newItem
    }

}