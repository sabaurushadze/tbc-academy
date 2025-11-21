package com.example.academy_tbc.presentation.screen.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.ItemMessageBinding

class MessengerAdapter() :
    ListAdapter<MessageItem, MessengerAdapter.MessageViewHolder>(MessageDiffUtil()) {


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): MessageViewHolder {
        return MessageViewHolder(
            ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: MessageViewHolder, position: Int,
    ) {
        holder.bind(getItem(position))
    }

    inner class MessageViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: MessageItem) = with(binding) {
            ivProfilePicture.load(message.image) {
                placeholder(R.drawable.img)
                error(R.drawable.img_error)
                crossfade(true)
            }
            tvOwner.text = message.owner
            tvLastMessage.text = message.lastMessage
            tvLastActive.text = message.lastActive
            if (message.unreadMessages == 0) {
                tvUnreadMessages.visibility = View.GONE
            } else {
                tvUnreadMessages.text = message.unreadMessages.toString()
            }

            if (message.isTyping) {
                tvUnreadMessages.visibility = View.GONE
                ivTyping.visibility = View.VISIBLE
                tvLastMessage.setTextColor(
                    root.context.resources.getColor(
                        R.color.gray, root.context.theme
                    )
                )
            }

            when (message.lastMessageType) {
                MessageType.TEXT -> {}
                MessageType.VOICE -> {
                    ivMessageType.visibility = View.VISIBLE
                    ivMessageType.setImageResource(R.drawable.ic_recorder)
                }

                MessageType.FILE -> {
                    ivMessageType.visibility = View.VISIBLE
                    ivMessageType.setImageResource(R.drawable.ic_attachment)
                }
            }
        }
    }


}


class MessageDiffUtil : DiffUtil.ItemCallback<MessageItem>() {
    override fun areItemsTheSame(
        oldItem: MessageItem, newItem: MessageItem,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MessageItem, newItem: MessageItem,
    ): Boolean {
        return oldItem == newItem
    }

}