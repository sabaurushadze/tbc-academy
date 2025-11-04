package com.example.academy_tbc.screen.chat

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentChatBinding
import java.util.UUID

class ChatFragment : BaseFragment<FragmentChatBinding>(
    FragmentChatBinding::inflate
) {
    private val messengerAdapter by lazy { ChatAdapter() }

    val messages = mutableListOf<MessageItem>(

    )

    override fun bind() {
        binding.rvMessages.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvMessages.adapter = messengerAdapter
    }

    override fun listeners() {
        sendMessage()
    }

    private fun sendMessage() = with(binding) {
        ibSend.setOnClickListener {
            if (etMessageInput.text.isNullOrBlank()) {
                return@setOnClickListener
            }

            val currentTimeMillis = System.currentTimeMillis()

            messages.add(
                MessageItem(
                    id = UUID.randomUUID(),
                    message = etMessageInput.text.toString(),
                    sentDate = currentTimeMillis,
                    messageType = if (messages.size % 2 == 0) MessageType.THEIR else MessageType.MY
                )
            )
            messengerAdapter.submitList(messages.toList())
            rvMessages.post {
                rvMessages.smoothScrollToPosition(messages.size - 1)
            }
            etMessageInput.text?.clear()
        }
    }
}