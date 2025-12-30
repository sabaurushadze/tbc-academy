package com.example.academy_tbc.presentation.screen.categories

import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.databinding.FragmentCategoriesBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.categories.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoriesBinding>(
    FragmentCategoriesBinding::inflate
) {
    private val viewModel: CategoryViewModel by viewModels()
    private val categoryAdapter by lazy { CategoryAdapter() }

    override fun bind() {
        initCategoryAdapter()
    }

    override fun listeners() {
        observeState()
        observeSideEffects()
        filterByName()
    }

    private fun filterByName() {
        binding.etSearch.addTextChangedListener { text ->

            viewModel.onEvent(CategoryEvent.FetchEquipmentEvent(text.toString()))
        }
    }

    private fun initCategoryAdapter() {
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryAdapter
        }
    }

    private fun observeSideEffects() {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                CategorySideEffect.NavigateToHome -> {
                }

                is CategorySideEffect.ShowError ->
                    binding.root.showSnackBar(effect.error.getString(requireContext()))
            }
        }
    }

    private fun observeState() {
        lifecycleCollectLatest(viewModel.state) { state ->
            binding.progressBar.isVisible = state.isLoading
            state.equipments?.let {
                categoryAdapter.submitList(state.equipments) {
                    binding.rvCategories.scrollToPosition(0)
                }
            }
        }
    }
}