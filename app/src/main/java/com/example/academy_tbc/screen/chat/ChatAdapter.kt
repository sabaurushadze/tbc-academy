package com.example.academy_tbc.screen.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.MyMessageItemBinding
import com.example.academy_tbc.databinding.TheirMessageItemBinding
import com.example.academy_tbc.utils.formatMessageTime

class ChatAdapter() :
    ListAdapter<MessageItem, RecyclerView.ViewHolder>(MessageDiffUtil()) {


    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) ITEM_THEIR_MESSAGE else ITEM_MY_MESSAGE
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == ITEM_MY_MESSAGE) {
            MyMessageViewHolder(
                MyMessageItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        } else {
            return TheirMessageViewHolder(
                TheirMessageItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            is MyMessageViewHolder -> holder.bind(getItem(position))
            is TheirMessageViewHolder -> holder.bind(getItem(position))
        }
    }

    inner class MyMessageViewHolder(private val binding: MyMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: MessageItem) = with(binding) {
            tvMessage.text = message.message
            tvMessageSentDate.text = formatMessageTime(itemView.context, message.sentDate)
        }
    }

    inner class TheirMessageViewHolder(private val binding: TheirMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: MessageItem) = with(binding) {
            tvMessage.text = message.message
            tvMessageSentDate.text = formatMessageTime(itemView.context, message.sentDate)
        }
    }

    companion object {
        private const val ITEM_MY_MESSAGE = 1
        private const val ITEM_THEIR_MESSAGE = 2
    }
}


class MessageDiffUtil : DiffUtil.ItemCallback<MessageItem>() {
    override fun areItemsTheSame(
        oldItem: MessageItem,
        newItem: MessageItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MessageItem,
        newItem: MessageItem
    ): Boolean {
        return oldItem == newItem
    }
}