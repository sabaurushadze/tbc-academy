package com.example.academy_tbc.presentation.screen.home

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentHomeBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.dp
import com.example.academy_tbc.presentation.extension.hideKeyboard
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.category.adapter.CategoryAdapter
import com.example.academy_tbc.presentation.screen.home.FilterBottomSheet.Companion.BUNDLE_KEY_CONDITION
import com.example.academy_tbc.presentation.screen.home.FilterBottomSheet.Companion.BUNDLE_KEY_MAX_PRICE
import com.example.academy_tbc.presentation.screen.home.FilterBottomSheet.Companion.BUNDLE_KEY_MIN_PRICE
import com.example.academy_tbc.presentation.screen.home.FilterBottomSheet.Companion.REQUEST_KEY_FILTER
import com.example.academy_tbc.presentation.screen.home.SortBottomSheet.Companion.BUNDLE_KEY_CHECKED_ID
import com.example.academy_tbc.presentation.screen.home.SortBottomSheet.Companion.REQUEST_KEY_SORT
import com.example.academy_tbc.presentation.screen.home.adapter.PcPartsLoadStateAdapter
import com.example.academy_tbc.presentation.screen.home.adapter.PcPartsPagingAdapter
import com.example.academy_tbc.presentation.screen.home.adapter.VerticalSpaceDecoration
import com.example.academy_tbc.presentation.screen.home.model.PcPartsQueryUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels()
    private val pcPartsPagingAdapter by lazy {
        PcPartsPagingAdapter(
            onClick = {}
        )
    }

    private val pcPartsAdapterWithFooter by lazy {
        pcPartsPagingAdapter.withLoadStateFooter(
            PcPartsLoadStateAdapter { pcPartsPagingAdapter.retry() }
        )
    }


    private val categoriesAdapter by lazy {
        CategoryAdapter(
            onClick = { item ->
                viewModel.onEvent(HomeEvent.GetPartsByCategory(PcPartsQueryUi(category = item.category)))
                viewModel.onEvent(HomeEvent.SaveCategory(item.id))
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_KEY_FILTER) { _, bundle ->
            val currentQuery = viewModel.state.value.query

            val newQuery = currentQuery.copy(
                minPrice = bundle.getFloat(BUNDLE_KEY_MIN_PRICE),
                maxPrice = bundle.getFloat(BUNDLE_KEY_MAX_PRICE),
                condition = bundle.getString(BUNDLE_KEY_CONDITION)
            )
            viewModel.onEvent(HomeEvent.Search(newQuery))
        }

        setFragmentResultListener(REQUEST_KEY_SORT) { _, bundle ->
            val checkedId = bundle.getInt(BUNDLE_KEY_CHECKED_ID)
            val sortBy = when (checkedId) {
                R.id.rbLowestPrice -> false
                else -> true
            }

            val currentQuery = viewModel.state.value.query

            val newQuery = currentQuery.copy(
                sortBy = PRICE,
                sortDescending = sortBy
            )
            viewModel.onEvent(HomeEvent.Search(newQuery))
        }
    }

    override fun bind() {
        setUpPcPartsAdapter()
        setUpCategoriesAdapter()
    }

    override fun listeners() {
        onFilterClick()
        observeSideEffects()
        observeState()
        observePagingData()
        search()
        onSortClick()
    }

    private fun setUpCategoriesAdapter() {
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
            setPadding(12.dp, 0, 12.dp, 0)
        }
    }

    private fun onFilterClick() {
        binding.btnFilter.setOnClickListener {
            val filterBottomSheet = FilterBottomSheet()
            filterBottomSheet.show(parentFragmentManager, BOTTOM_SHEET_FILTER_TAG)
        }
    }

    private fun setUpPcPartsAdapter() {
        binding.rvParts.apply {
            adapter = pcPartsAdapterWithFooter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            addItemDecoration(VerticalSpaceDecoration(4.dp))
        }
    }

    private fun observeSideEffects() {
        lifecycleCollectLatest(viewModel.effect) { effect ->
            when (effect) {
                is HomeSideEffect.ShowError -> binding.root.showSnackBar(
                    effect.error.getString(
                        requireContext()
                    )
                )
            }
        }
    }

    private fun observePagingData() {
        lifecycleCollectLatest(viewModel.partsPagingFlow) { pagingData ->
            pcPartsPagingAdapter.submitData(pagingData)
        }
    }

    private fun observeState() {
        lifecycleCollectLatest(viewModel.state) { state ->
            categoriesAdapter.submitList(state.categories)
        }
    }

    private fun onSortClick() {
        binding.btnSort.setOnClickListener {
            val sheet = SortBottomSheet()
            sheet.show(parentFragmentManager, BOTTOM_SHEET_SORT_TAG)
        }
    }

    private fun search() = with(binding) {
        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = etSearch.text.toString()
                viewModel.onEvent(HomeEvent.Search(PcPartsQueryUi(titleLike = query)))
                etSearch.hideKeyboard()
                true
            } else false
        }
    }

    companion object {
        private const val PRICE = "price"

        private const val BOTTOM_SHEET_SORT_TAG = "sheet_sort"
        private const val BOTTOM_SHEET_FILTER_TAG = "sheet_filter"
    }
}