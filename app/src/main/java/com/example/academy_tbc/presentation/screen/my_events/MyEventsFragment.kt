package com.example.academy_tbc.presentation.screen.my_events

import androidx.fragment.app.viewModels
import com.example.academy_tbc.databinding.FragmentMyEventsBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyEventsFragment : BaseFragment<FragmentMyEventsBinding>(
    FragmentMyEventsBinding::inflate
) {
    private val viewModel: MyEventsViewModel by viewModels()

    override fun bind() {
    }

    override fun listeners() {
        observeState()
        observeSideEffects()

    }

    private fun observeSideEffects() = with(binding) {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                is MyEventsSideEffect.ShowError ->
                    root.showSnackBar(effect.error.getString(requireContext()))

            }
        }
    }

    private fun observeState() {
        lifecycleCollectLatest(viewModel.state) { state ->

        }
    }

}