package com.example.challenge.presentation.screen.log_in

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.challenge.presentation.common.BaseFragment
import com.example.challenge.databinding.FragmentLogInBinding
import com.example.challenge.presentation.extension.lifecycleCollectLatest
import com.example.challenge.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
//class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {
class  LogInFragment : BaseFragment<FragmentLogInBinding>(
    FragmentLogInBinding::inflate
) {
    private val viewModel: LogInViewModel by viewModels()

    override fun listeners() {
        onLoginButtonClick()
        observeState()
        observeSideEffects()
    }

    override fun observers() {

    }

    private fun onLoginButtonClick() {
        binding.btnLogIn.setOnClickListener {
            logIn()
        }
    }

    private fun observeState() {
        lifecycleCollectLatest(viewModel.logInState) { state ->
            handleLogInState(state)
//            grishas salami
        }
    }

    private fun observeSideEffects() {
        lifecycleCollectLatest(viewModel.sideEffect) { event ->
            when (event) {
                LogInSideEffect.NavigateToConnections -> {
                    findNavController().navigate(
                        LogInFragmentDirections.actionLogInFragmentToConnectionsFragment()
                    )
                }
            }
        }
    }

    private fun logIn() {
        viewModel.onEvent(
            LogInEvent.LogIn(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        )
    }

    private fun handleLogInState(logInState: LogInState) {
        binding.loaderInclude.loaderContainer.visibility =
            if (logInState.isLoading) View.VISIBLE else View.GONE

        logInState.errorMessage?.let {
            binding.root.showSnackBar(message = it)
//            viewModel.onEvent(LogInEvent.ResetErrorMessage)
        }
    }
}
