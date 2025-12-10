package com.example.academy_tbc.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemLocationBinding
import com.example.academy_tbc.presentation.extension.loadImage
import com.example.academy_tbc.presentation.screen.home.model.LocationUi

class LocationAdapter() :
    ListAdapter<LocationUi, LocationAdapter.LocationViewHolder>(LocationDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): LocationViewHolder {
        return LocationViewHolder(
            ItemLocationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class LocationViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: LocationUi) = with(binding) {
            ivLocation.loadImage(location.cover)
            tvTitle.text = location.title
        }
    }
}

class LocationDiffUtil : DiffUtil.ItemCallback<LocationUi>() {
    override fun areItemsTheSame(
        oldItem: LocationUi, newItem: LocationUi,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: LocationUi, newItem: LocationUi,
    ): Boolean {
        return oldItem == newItem
    }

}