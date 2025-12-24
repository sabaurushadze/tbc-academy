package com.example.academy_tbc.presentation.screen.home.upcoming_events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.ItemUpcomingEventBinding
import com.example.academy_tbc.presentation.screen.home.upcoming_events.model.UpcomingEventUi

class UpcomingEventAdapter(
    val onClick: (Int) -> Unit,
) :
    ListAdapter<UpcomingEventUi, UpcomingEventAdapter.UserViewHolder>(EventDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): UserViewHolder {
        return UserViewHolder(
            ItemUpcomingEventBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: ItemUpcomingEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: UpcomingEventUi) = with(binding) {
            tvMonthAbbreviation.text = event.monthAbbreviation
            tvMonthNumber.text = event.monthNumber
            tvTitle.text = event.title
            tvEventTime.text = event.time
            tvLocation.text = event.location
            tvDescription.text = event.description
            tvCurrentCapacity.text =
                root.context.getString(R.string.registered, event.currentCapacity)
            tvAvailableSlots.text =
                root.context.getString(R.string.spots_left, event.availableSlots)

            tvBtnViewDetails.setOnClickListener {
                onClick(event.id)
            }
        }
    }
}

class EventDiffUtil : DiffUtil.ItemCallback<UpcomingEventUi>() {
    override fun areItemsTheSame(
        oldItem: UpcomingEventUi, newItem: UpcomingEventUi,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UpcomingEventUi, newItem: UpcomingEventUi,
    ): Boolean {
        return oldItem == newItem
    }

}