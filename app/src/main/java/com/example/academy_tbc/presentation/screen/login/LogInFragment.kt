package com.example.academy_tbc.presentation.screen.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.databinding.FragmentLogInBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.home.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(
    FragmentLogInBinding::inflate
) {
    private val viewModel: LogInViewModel by viewModels()

    override fun bind() {}

    override fun listeners() {
        observeState()
        observeSideEffects()
        onLogInButtonClick()
    }

    private fun observeSideEffects() {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                LogInSideEffect.NavigateToHome -> {
                    findNavController().navigate(
                        LogInFragmentDirections.actionLogInFragmentToHomeFragment()
                    )
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

    private fun onLogInButtonClick() = with(binding) {
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val rememberMe = cbRememberMe.isChecked
            viewModel.onEvent(LogInEvent.LogIn(
                email = email,
                password = password,
                rememberMe = rememberMe
            ))
        }
    }
}