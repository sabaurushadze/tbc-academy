package com.example.academy_tbc.presentation.screen.chat

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.databinding.FragmentChatBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import com.example.academy_tbc.presentation.extension.showSnackBar
import kotlinx.coroutines.launch

class ChatFragment : BaseFragment<FragmentChatBinding>(
    FragmentChatBinding::inflate
) {
    private val chatAdapter by lazy { MessengerAdapter() }

    private val viewModel: ChatViewModel by activityViewModels()

    override fun bind() {
        viewModel.onEvent(ChatEvent.GetUsers)
        binding.rvMessages.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvMessages.adapter = chatAdapter
    }

    override fun listeners() {
        onSearchButtonClick()
        observeState()
        observeSideEffects()
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    chatAdapter.submitList(state.messages)
                    binding.progressBar.isVisible = state.isLoading
                }
            }
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { effect ->
                    when (effect) {
                        is ChatSideEffect.ShowError -> binding.root.showSnackBar(effect.message)
                    }
                }
            }
        }
    }

    private fun onSearchButtonClick() {
        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()
            viewModel.onEvent(ChatEvent.FilterChat(query))
        }
    }

}
