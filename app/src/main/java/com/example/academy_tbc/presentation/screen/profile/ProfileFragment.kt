package com.example.academy_tbc.presentation.screen.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentProfileBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.gone
import com.example.academy_tbc.presentation.extension.hideKeyboard
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.loadImage
import com.example.academy_tbc.presentation.extension.show
import com.example.academy_tbc.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    private val viewModel: ProfileViewModel by viewModels()

    override fun listeners() {
        observeState()
        signOut()
        observeSideEffect()
        setUpApplyClick()
        setupEditClick()
        setUpInitialUi()
    }

    private fun setUpInitialUi() = with(binding) {
        etUsername.isEnabled = false
        ivEdit.show()
        ivApply.gone()
    }

    private fun observeState() = with(binding) {
        lifecycleCollectLatest(viewModel.state) { state ->
            val userProfile = state.user

            if (!etUsername.isEnabled) {
                etUsername.setText(userProfile?.displayName)
            }
            ivProfilePicture.loadImage(
                url = userProfile?.photoUrl,
                placeholderRes = R.drawable.pfp_placeholder
            )
        }
    }

    private fun observeSideEffect() = with(binding) {
        lifecycleCollectLatest(viewModel.effect) { effect ->
            when (effect) {
                ProfileSideEffect.NavigateToSignIn -> findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToSignInFragment()
                )

                is ProfileSideEffect.ShowError -> {
                    root.showSnackBar(effect.error.getString(requireContext()))
                    etUsername.isEnabled = true
                    ivApply.show()
                    ivEdit.gone()
                }

                is ProfileSideEffect.UpdateUserNameSuccess -> {
                    etUsername.isEnabled = false
                    ivApply.gone()
                    ivEdit.show()
                    root.showSnackBar(getString(R.string.username_changed))
                }
            }
        }
    }

    private fun setupEditClick() = with(binding) {
        ivEdit.setOnClickListener {
            etUsername.isEnabled = true
            etUsername.requestFocus()
            ivEdit.gone()
            ivApply.show()
        }
    }

    private fun setUpApplyClick() = with(binding) {
        ivApply.setOnClickListener {
            val newUserName = etUsername.text.toString().trim()
            root.hideKeyboard()
            etUsername.clearFocus()
            viewModel.onEvent(ProfileEvent.UpdateUserName(newUserName))
        }
    }

    private fun signOut() {
        binding.btnSignOut.setOnClickListener {
            viewModel.onEvent(ProfileEvent.SignOut)
        }
    }

}