package com.example.academy_tbc.presentation.screen.notifications

import androidx.fragment.app.viewModels
import com.example.academy_tbc.databinding.FragmentNotificationsBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(
    FragmentNotificationsBinding::inflate
) {
    private val viewModel: NotificationsViewModel by viewModels()

    override fun bind() {
    }

    override fun listeners() {
        observeState()
        observeSideEffects()

    }

    private fun observeSideEffects() = with(binding) {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                is NotificationsSideEffect.ShowError ->
                    root.showSnackBar(effect.error.getString(requireContext()))

            }
        }
    }

    private fun observeState() {
        lifecycleCollectLatest(viewModel.state) { state ->

        }
    }

}