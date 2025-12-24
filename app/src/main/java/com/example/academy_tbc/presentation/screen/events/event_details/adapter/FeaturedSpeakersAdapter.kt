package com.example.academy_tbc.presentation.screen.events.event_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemFeaturedSpeakerBinding
import com.example.academy_tbc.presentation.extension.loadImage
import com.example.academy_tbc.presentation.screen.events.event_details.model.FeaturedSpeakerUi

class FeaturedSpeakersAdapter() :
    ListAdapter<FeaturedSpeakerUi, FeaturedSpeakersAdapter.UserViewHolder>(EventCategoryDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): UserViewHolder {
        return UserViewHolder(
            ItemFeaturedSpeakerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserViewHolder(private val binding: ItemFeaturedSpeakerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(speaker: FeaturedSpeakerUi) = with(binding) {
            tvSpeaker.text = speaker.name
            tvRole.text = speaker.role
            ivSpeaker.loadImage(speaker.photoUrl)
        }
    }
}

class EventCategoryDiffUtil : DiffUtil.ItemCallback<FeaturedSpeakerUi>() {
    override fun areItemsTheSame(
        oldItem: FeaturedSpeakerUi, newItem: FeaturedSpeakerUi,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: FeaturedSpeakerUi, newItem: FeaturedSpeakerUi,
    ): Boolean {
        return oldItem == newItem
    }

}