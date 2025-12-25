package com.example.academy_tbc.presentation.screen.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.databinding.FragmentProfileBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    private val viewModel: ProfileViewModel by viewModels()

    override fun bind() {
    }

    override fun listeners() {
        observeState()
        observeSideEffects()
        signOut()
    }

    private fun observeSideEffects() = with(binding) {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                is ProfileSideEffect.ShowError ->
                    root.showSnackBar(effect.error.getString(requireContext()))

                ProfileSideEffect.NavigateToSignIn -> {
                    findNavController().navigate(
                        ProfileFragmentDirections.actionProfileFragmentToSignInFragment()
                    )
                }
            }
        }
    }

    private fun observeState() = with(binding) {
        lifecycleCollectLatest(viewModel.state) { state ->
            tvFullName.text = state.fullName
            tvEmail.text = state.email
            tvDepartment.text = state.department
        }
    }

    private fun signOut() {
        binding.btnSignOut.setOnClickListener {
            viewModel.onEvent(ProfileEvent.SignOut)
        }
    }

}