package com.example.academy_tbc.presentation.screen.home

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentHomeBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.dp
import com.example.academy_tbc.presentation.extension.hideKeyboard
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.home.FilterBottomSheet.Companion.BUNDLE_KEY_MAX_PRICE
import com.example.academy_tbc.presentation.screen.home.FilterBottomSheet.Companion.BUNDLE_KEY_MIN_PRICE
import com.example.academy_tbc.presentation.screen.home.FilterBottomSheet.Companion.BUNDLE_KEY_SELECTED_OPTIONS
import com.example.academy_tbc.presentation.screen.home.FilterBottomSheet.Companion.REQUEST_KEY_FILTER
import com.example.academy_tbc.presentation.screen.home.SortBottomSheet.Companion.BUNDLE_KEY_CHECKED_ID
import com.example.academy_tbc.presentation.screen.home.SortBottomSheet.Companion.REQUEST_KEY_SORT
import com.example.academy_tbc.presentation.screen.home.adapter.decoration.VerticalSpaceDecoration
import com.example.academy_tbc.presentation.screen.home.adapter.paging.PcPartsLoadStateAdapter
import com.example.academy_tbc.presentation.screen.home.adapter.paging.PcPartsPagingAdapter
import com.example.academy_tbc.presentation.screen.home.category.adapter.CategoryAdapter
import com.example.academy_tbc.presentation.screen.home.model.PcPartsQueryUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels()
    private val pcPartsPagingAdapter by lazy {
        PcPartsPagingAdapter(
            onClick = { pcPart ->
                setFragmentResult(REQUEST_KEY_ID, bundleOf(BUNDLE_KEY_ID to pcPart.id))
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPartDetailFragment())
            }
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

        setFragmentResultListener(REQUEST_KEY_FILTER) { _, bundle ->
            val currentQuery = viewModel.state.value.query

            val minPrice = bundle.getFloat(BUNDLE_KEY_MIN_PRICE).takeIf { it != 0f }
            val maxPrice = bundle.getFloat(BUNDLE_KEY_MAX_PRICE).takeIf { it != 0f }

            @Suppress("DEPRECATION")
            val selectedOptions = bundle.getSerializable(BUNDLE_KEY_SELECTED_OPTIONS)
                    as? Map<String, List<String>> ?: emptyMap()

            val newQuery = currentQuery.copy(
                minPrice = minPrice,
                maxPrice = maxPrice,
                filters = selectedOptions
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
        refreshOnSwipe()
    }

    private fun refreshOnSwipe() {
        binding.swipeRefresh.setOnRefreshListener {
            pcPartsPagingAdapter.refresh()
            viewModel.onEvent(HomeEvent.GetCategories)
        }
    }


    private fun setUpCategoriesAdapter() {
        binding.rvCategories.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
            setPadding(12.dp, 0, 12.dp, 0)
        }
    }


    private fun setUpPcPartsAdapter() {
        binding.rvParts.apply {
            itemAnimator = null
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
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun observeState() {
        lifecycleCollectLatest(viewModel.state) { state ->
            categoriesAdapter.submitList(state.categories)
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun onFilterClick() {
        binding.btnFilter.setOnClickListener {
            val categoryId = viewModel.state.value.category
            setFragmentResult(REQUEST_KEY_ID_FILTER, bundleOf(BUNDLE_KEY_ID_FILTER to categoryId))
            val filterBottomSheet = FilterBottomSheet()
            filterBottomSheet.show(parentFragmentManager, BOTTOM_SHEET_FILTER_TAG)
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

        const val REQUEST_KEY_ID = "request_key_id"
        const val REQUEST_KEY_ID_FILTER = "request_key_id_filter"
        const val BUNDLE_KEY_ID = "bundle_key_id"
        const val BUNDLE_KEY_ID_FILTER = "bundle_key_id_filter"
    }
}