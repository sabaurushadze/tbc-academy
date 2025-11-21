package com.example.academy_tbc.presentation.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.common.Resource
import com.example.academy_tbc.data.repository.ChatRepository
import com.example.academy_tbc.presentation.common.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    val isConnected = connectivityObserver.isConnected.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        false
    )

    private fun observeConnectivityAndGetUsers() {
        viewModelScope.launch {
            isConnected.collect { connected ->
                if (connected) {
                    getUsers()
                }
            }
        }
    }

    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ChatSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()
    private var fullChatList: List<MessageItem> = emptyList()

    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.GetUsers -> observeConnectivityAndGetUsers()
            is ChatEvent.FilterChat -> filterChats(query = event.query)
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            chatRepository.getChatMessages().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        val dtoList = result.data

                        val mapped = dtoList.map {
                            MessageItem(
                                id = it.id,
                                image = it.image,
                                owner = it.owner,
                                lastMessage = it.lastMessage,
                                lastActive = it.lastActive,
                                unreadMessages = it.unreadMessages,
                                isTyping = it.isTyping,
                                lastMessageType = MessageType.fromString(it.lastMessageType)
                            )
                        }

                        fullChatList = mapped
                        _state.update {
                            it.copy(
                                messages = mapped
                            )
                        }
                    }

                    is Resource.Error -> _sideEffect.emit(ChatSideEffect.ShowError(result.errorMessage))
                }
            }
        }
    }

    private fun filterChats(query: String) {
        val filtered = if (query.isBlank()) {
            fullChatList
        } else {
            fullChatList.filter { it.owner.contains(query, ignoreCase = true) }
        }
        _state.update { it.copy(messages = filtered) }
    }

}