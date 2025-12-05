package com.example.academy_tbc.presentation.screen.home

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.academy_tbc.databinding.FragmentHomeBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.home.adapter.StatsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels()
    private val statisticsAdapter by lazy { StatsAdapter() }

    override fun bind() {
        viewModel.onEvent(HomeEvent.LoadStats)
    }
    override fun listeners() {
        setupViewPager()
        observeSideEffects()
        observeState()
    }

    private fun setupViewPager() {
        val transformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(60))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
                page.scaleX = 0.85f + r * 0.15f
                page.alpha = 0.5f + r * 0.5f
            }
        }

        binding.viewPager.apply {
            adapter = statisticsAdapter
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            setPadding(80, 0, 80, 0)
            setPageTransformer(transformer)
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect { effect ->
                    when (effect) {


                        is HomeSideEffect.ShowError -> binding.root.showSnackBar(getString(effect.error))
                    }
                }
            }
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.progressBar.isVisible = state.isLoading

                    if (state.stats != null) {
                        statisticsAdapter.submitList(state.stats)
                    }
                }
            }
        }
    }
}