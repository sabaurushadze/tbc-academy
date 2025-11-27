package com.example.academy_tbc.presentation.screen.admin

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentAdminBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdminFragment : BaseFragment<FragmentAdminBinding>(
    FragmentAdminBinding::inflate
) {
    private val viewModel: AdminViewModel by viewModels()

    override fun listeners() {
        saveUserSettings()
        readUserSettings()
        observeSideEffects()
    }

    private fun observeSideEffects() {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                is AdminSideEffect.ShowError -> binding.root.showSnackBar(
                    getString(effect.errorRes)
                )

                AdminSideEffect.Success -> binding.root.showSnackBar(
                    getString(R.string.settings_saved_successfully)
                )
            }
        }
    }

    private fun saveUserSettings() = with(binding) {
        btnSave.setOnClickListener {
            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val email = etEmail.text.toString().trim()

            viewModel.onEvent(
                AdminEvent.SaveUser(
                    firstName = firstName, lastName = lastName, email = email
                )
            )
            clearAllInputs()
        }
    }

    private fun readUserSettings() = with(binding) {
        btnRead.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val settings = viewModel.userSettings.first()
                tvFirstName.text = settings.firstName
                tvLastName.text = settings.lastName
                tvEmail.text = settings.email
            }
            clearAllInputs()
        }
    }

    private fun clearAllInputs() = with(binding) {
        etEmail.text?.clear()
        etFirstName.text?.clear()
        etLastName.text?.clear()
    }

}