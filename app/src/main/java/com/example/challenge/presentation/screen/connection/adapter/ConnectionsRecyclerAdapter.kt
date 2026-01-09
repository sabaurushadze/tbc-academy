package com.example.challenge.presentation.screen.connection.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge.databinding.ItemConnectionLayoutBinding
import com.example.challenge.presentation.extension.loadImage
import com.example.challenge.presentation.screen.connection.model.UiConnection

class ConnectionsRecyclerAdapter :
    ListAdapter<UiConnection, ConnectionsRecyclerAdapter.ConnectionsViewHolder>(ConnectionsDiffUtil()) {

//    @Inject
//    @ApplicationContext
//    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ConnectionsViewHolder(
        ItemConnectionLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )


    override fun onBindViewHolder(holder: ConnectionsViewHolder, position: Int) {
//        holder.bind()
        holder.bind(getItem(position))
    }

    inner class ConnectionsViewHolder(private val binding: ItemConnectionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
//        private lateinit var model: UiConnection

        fun bind(connection: UiConnection) {
//            model = currentList[adapterPosition]
            binding.apply {
                imvProfile.loadImage(connection.avatar)
                tvFullName.text = connection.fullName
            }
        }
    }
}

class ConnectionsDiffUtil : DiffUtil.ItemCallback<UiConnection>() {
    override fun areItemsTheSame(oldItem: UiConnection, newItem: UiConnection): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UiConnection, newItem: UiConnection): Boolean {
        return oldItem == newItem
    }
}