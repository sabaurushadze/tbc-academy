package com.example.academy_tbc.presentation.screen.auth.forgot_password

import androidx.fragment.app.viewModels
import com.example.academy_tbc.databinding.FragmentForgotPasswordBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(
    FragmentForgotPasswordBinding::inflate
) {
    private val viewModel: ForgotPasswordViewModel by viewModels()


    override fun bind() {
    }

    override fun listeners() {
        observeState()
        observeSideEffects()
    }

    private fun observeSideEffects() = with(binding) {
        lifecycleCollectLatest(viewModel.effect) { effect ->
            when (effect) {
                else -> {}
            }
        }
    }

    private fun observeState() {
        lifecycleCollect(viewModel.state) { state ->
        }
    }


}