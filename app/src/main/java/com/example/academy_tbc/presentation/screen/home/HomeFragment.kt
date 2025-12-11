package com.example.academy_tbc.presentation.screen.home

import android.os.Bundle
import android.util.Log.d
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentHomeBinding
import com.example.academy_tbc.domain.model.pc_parts.PcPartsQuery
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.dp
import com.example.academy_tbc.presentation.extension.hideKeyboard
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.category.CategoryFragment.Companion.BUNDLE_KEY_CATEGORY
import com.example.academy_tbc.presentation.screen.category.CategoryFragment.Companion.REQUEST_KEY_CATEGORY
import com.example.academy_tbc.presentation.screen.home.SortBottomSheet.Companion.BUNDLE_KEY_CHECKED_ID
import com.example.academy_tbc.presentation.screen.home.SortBottomSheet.Companion.REQUEST_KEY_SORT
import com.example.academy_tbc.presentation.screen.home.adapter.PcPartsAdapter
import com.example.academy_tbc.presentation.screen.home.adapter.VerticalSpaceDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels()
    private val pcPartsAdapter by lazy {
        PcPartsAdapter(onClick = {})
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_KEY_CATEGORY) { _, bundle ->
            val category = bundle.getInt(BUNDLE_KEY_CATEGORY)
            viewModel.onEvent(HomeEvent.GetPartsByCategory(PcPartsQuery(category = category)))
        }

        setFragmentResultListener(REQUEST_KEY_SORT) { _, bundle ->
            val checkedId = bundle.getInt(BUNDLE_KEY_CHECKED_ID)
            val sortBy = when(checkedId) {
                R.id.rbLowestPrice -> false
                else -> true
            }
            viewModel.onEvent(HomeEvent.GetPartsByCategory(PcPartsQuery(sortBy = PRICE, sortDescending = sortBy)))
        }
    }


    override fun bind() {
        setUpPcPartsAdapter()
    }

    override fun listeners() {
//        viewModel.onEvent(HomeEvent.GetParts)
        observeSideEffects()
        observeState()
        search()
        sortByLowestPrice()
        val sheet = SortBottomSheet()
        sheet.show(parentFragmentManager, BOTTOM_SHEET_SORT_TAG)
//        signOut()
    }

    private fun setUpPcPartsAdapter() {
        binding.rvParts.apply {
            adapter = pcPartsAdapter
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
                is HomeSideEffect.ShowError -> binding.root.showSnackBar(effect.message)
//                HomeSideEffect.NavigateToSignIn -> findNavController().navigate(
//                    HomeFragmentDirections.actionHomeFragmentToSignInFragment()
//                )
            }
        }
    }

    private fun observeState() {
        lifecycleCollectLatest(viewModel.partsPagingFlow) { pagingData ->
            pcPartsAdapter.submitData(pagingData)

        }
    }

    private fun sortByLowestPrice() {
        binding.btnSort.setOnClickListener {
            viewModel.onEvent(
                HomeEvent.Search(
                    PcPartsQuery(
                        sortBy = PRICE,
                        condition = NEW,
                        maxPrice = 1300f
                    )
                )
            )
        }
    }

    private fun search() = with(binding) {
        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = etSearch.text.toString()
                viewModel.onEvent(HomeEvent.Search(PcPartsQuery(titleLike = query)))
                etSearch.hideKeyboard()
                true
            } else false
        }
    }

    companion object {
        private const val PRICE = "price"
        private const val NEW = "new"

        private const val BOTTOM_SHEET_SORT_TAG = "sheet_sort"
    }
}