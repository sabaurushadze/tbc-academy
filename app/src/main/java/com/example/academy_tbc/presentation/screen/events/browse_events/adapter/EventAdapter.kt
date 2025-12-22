package com.example.academy_tbc.presentation.screen.events.browse_events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemEventBinding
import com.example.academy_tbc.databinding.ItemUpcomingEventBinding
import com.example.academy_tbc.domain.model.home.categories.CategoryType
import com.example.academy_tbc.presentation.screen.events.browse_events.model.EventUi
import com.example.academy_tbc.presentation.screen.home.categories.mapper.getCategoryNameRes
import com.example.academy_tbc.presentation.util.DateTimeParser

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
            tvMonthAbbreviation.text = DateTimeParser.getMonthAbbr(event.startDateTime)
            tvMonthNumber.text = DateTimeParser.getDay(event.startDateTime).toString()
            tvTitle.text = event.title
            tvEventTime.text = "${DateTimeParser.getTime(event.startDateTime)} - ${DateTimeParser.getTime(event.endDateTime)}"
            tvLocation.text = event.location
            tvCapacity.text = event.capacity.toString()
            tvCategory.text = root.context.getString(getCategoryNameRes(event.eventTypeId))

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