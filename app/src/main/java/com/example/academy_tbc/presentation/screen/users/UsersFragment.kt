package com.example.academy_tbc.presentation.screen.users

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentUsersBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.users.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>(
    FragmentUsersBinding::inflate
) {
    private val viewModel: UsersViewModel by viewModels()
    private val usersAdapter by lazy {
        UsersAdapter(onUserClick = { user ->
            binding.root.showSnackBar(getString(R.string.clicked, user.fullName))
        }, onUserLongClick = { user ->
            viewModel.onEvent(UsersEvent.DeleteUser(user))
        })
    }

    override fun bind() {
        binding.rvUsers.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvUsers.adapter = usersAdapter
    }

    override fun listeners() {
        observeSideEffects()
        observeState()
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is UsersSideEffect.ShowError -> {
                            binding.root.showSnackBar(getString(effect.error))
                        }

                        UsersSideEffect.ShowOffline -> binding.root.showSnackBar(getString(R.string.no_internet_connection_please_try_again))
                        UsersSideEffect.ShowOnline -> binding.root.showSnackBar(getString(R.string.online))
                    }
                }
            }
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.users != null) {
                        usersAdapter.submitList(state.users)
                    }
                    binding.progressBar.isVisible = state.isLoading
                }
            }
        }
    }
}