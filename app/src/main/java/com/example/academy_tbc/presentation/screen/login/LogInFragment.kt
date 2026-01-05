package com.example.academy_tbc.presentation.screen.login

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.databinding.FragmentLogInBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.login.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(
    FragmentLogInBinding::inflate
) {
    private val viewModel: LogInViewModel by viewModels()
    private val usersAdapter by lazy { UsersAdapter() }

    override fun bind() {
        binding.rvUsers.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvUsers.adapter = usersAdapter
    }

    override fun listeners() {
        observeState()
        observeSideEffects()
    }

    private fun observeSideEffects() {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                LogInSideEffect.NavigateToHome -> {
                }

                is LogInSideEffect.ShowSnackBar -> {
                    binding.root.showSnackBar(getString(effect.errorRes))

                }
            }
        }
    }

    private fun observeState() {
        lifecycleCollectLatest(viewModel.state) { state ->

        }
    }
}