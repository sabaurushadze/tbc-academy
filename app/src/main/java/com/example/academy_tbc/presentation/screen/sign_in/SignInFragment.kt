package com.example.academy_tbc.presentation.screen.sign_in

import android.os.Bundle
import android.util.Log.d
import androidx.core.view.isVisible
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentSignInBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(
    FragmentSignInBinding::inflate
) {
    private val viewModel: SignInViewModel by viewModels()
    private lateinit var credentialManager: CredentialManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        credentialManager = CredentialManager.create(requireContext())
    }

    override fun listeners() {
        onGoogleSignInClick()
        observeState()
        observeSideEffects()
        onSignUpClick()
        signInWithEmailAndPassword()
    }

    private fun observeState() {
        lifecycleCollect(viewModel.state) { state ->
            binding.btnSignInGoogle.isEnabled = !state.isLoading
            binding.progressBar.isVisible = state.isLoading
        }
    }

    private fun observeSideEffects() {
        lifecycleCollect(viewModel.effect) { effect ->
            when (effect) {
                SignInSideEffect.NavigateToHome -> findNavController().navigate(
                    SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                )

                is SignInSideEffect.ShowError -> {
                    binding.root.showSnackBar(effect.message)
                }
            }
        }
    }

    private fun onGoogleSignInClick() {
        binding.btnSignInGoogle.setOnClickListener {
            launchGoogleSignIn()
        }
    }

    private fun signInWithEmailAndPassword() = with(binding) {
        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            viewModel.onEvent(
                SignInEvent.SignInWithEmailAndPassword(
                    email = email,
                    password = password
                )
            )
        }

    }

    private fun onSignUpClick() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            )
        }
    }

    private fun launchGoogleSignIn() {

        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(getString(R.string.web_client_id))
            .setFilterByAuthorizedAccounts(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    context = requireContext(),
                    request = request
                )
                handleSignIn(result.credential)
            } catch (e: GetCredentialCancellationException) {
                d("asdd", "User cancelled credential selection")
            } catch (e: GetCredentialException) {
                d("asdd", "Credential error: ${e.message}")
                binding.root.showSnackBar("Sign-in failed: ${e.message}")
            } catch (e: Throwable) {
                d("asdd", "Unknown error: ${e.message}")
            }

        }
    }

    private fun handleSignIn(credential: Credential) {
        if (credential is CustomCredential &&
            credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val token = GoogleIdTokenCredential
                .createFrom(credential.data)
                .idToken

            viewModel.onEvent(SignInEvent.SignInWithGoogle(token))
        }
    }
}