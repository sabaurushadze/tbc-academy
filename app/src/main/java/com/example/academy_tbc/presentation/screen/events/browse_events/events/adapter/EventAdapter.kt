package com.example.academy_tbc.presentation.screen.events.browse_events.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.ItemEventBinding
import com.example.academy_tbc.presentation.screen.events.browse_events.events.model.EventUi

class EventAdapter(
    val onClick: (Int) -> Unit,
) :
    ListAdapter<EventUi, EventAdapter.UserViewHolder>(EventDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): UserViewHolder {
        return UserViewHolder(
            ItemEventBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventUi) = with(binding) {
            tvTitle.text = event.title
            tvLocation.text = event.location
            tvCategory.text = event.categoryTitle
            tvAvailableSlots.text = root.context.getString(R.string.spots_left_capacity, event.availableSlots)
            tvCurrentCapacity.text = root.context.getString(R.string.registered_capacity, event.currentCapacity)
            tvMonthNumber.text = event.monthNumber
            tvMonthAbbreviation.text = event.monthAbbreviation
            tvEventTime.text = event.time
            tvEventStatus.text = event.eventStatus

            root.setOnClickListener {
                onClick(event.id)
            }
        }
    }
}

class EventDiffUtil : DiffUtil.ItemCallback<EventUi>() {
    override fun areItemsTheSame(
        oldItem: EventUi, newItem: EventUi,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: EventUi, newItem: EventUi,
    ): Boolean {
        return oldItem == newItem
    }

}