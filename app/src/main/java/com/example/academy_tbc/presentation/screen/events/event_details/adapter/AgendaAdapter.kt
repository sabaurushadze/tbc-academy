package com.example.academy_tbc.presentation.screen.events.event_details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemAgendaBinding
import com.example.academy_tbc.presentation.screen.events.event_details.model.AgendaUi

class AgendaAdapter() :
    ListAdapter<AgendaUi, AgendaAdapter.UserViewHolder>(EventDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): UserViewHolder {
        return UserViewHolder(
            ItemAgendaBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: ItemAgendaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(agenda: AgendaUi) = with(binding) {

            tvTitle.text = "${agenda.time} - ${agenda.title}"
            tvDescription.text = agenda.description
            idNumber.text = (bindingAdapterPosition + 1).toString()
            val isLast = bindingAdapterPosition == this@AgendaAdapter.itemCount - 1
            divider.visibility = if (isLast) View.GONE else View.VISIBLE
        }
    }
}

class EventDiffUtil : DiffUtil.ItemCallback<AgendaUi>() {
    override fun areItemsTheSame(
        oldItem: AgendaUi, newItem: AgendaUi,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: AgendaUi, newItem: AgendaUi,
    ): Boolean {
        return oldItem == newItem
    }

}