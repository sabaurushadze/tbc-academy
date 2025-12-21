package com.example.academy_tbc.presentation.screen.home.upcoming_events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemUpcomingEventBinding
import com.example.academy_tbc.presentation.screen.home.upcoming_events.model.UpcomingEventUi
import com.example.academy_tbc.presentation.util.DateTimeParser

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
            tvMonthAbbreviation.text = DateTimeParser.getMonthAbbr(event.startDateTime)
            tvMonthNumber.text = DateTimeParser.getDay(event.startDateTime).toString()
            tvTitle.text = event.title
            tvEventTime.text = "${DateTimeParser.getTime(event.startDateTime)} - ${DateTimeParser.getTime(event.endDateTime)}"
            tvLocation.text = event.location
            tvDescription.text = event.description
            tvCapacity.text = event.capacity.toString()

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