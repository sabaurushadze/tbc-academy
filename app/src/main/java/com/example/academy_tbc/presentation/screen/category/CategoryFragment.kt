package com.example.academy_tbc.presentation.screen.category

import android.util.Log.d
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.academy_tbc.databinding.FragmentCategoryBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.dp
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.screen.category.adapter.CategoryAdapter
import com.example.academy_tbc.presentation.screen.category.adapter.InnerGridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(
    FragmentCategoryBinding::inflate
) {

    private val viewModel: CategoryViewModel by viewModels()
    private val categoriesAdapter by lazy {
        CategoryAdapter(
            onClick = { item ->
                d("asdd", "Category adapter item click category: ${item.category}")
                setFragmentResult(REQUEST_KEY_CATEGORY, bundleOf(BUNDLE_KEY_CATEGORY to item.category))
                findNavController().navigate(
                    CategoryFragmentDirections.actionCategoryFragmentToHomeFragment()
                )
            }
        )
    }


    override fun bind() {
        setUpCategoriesAdapter()
    }

    override fun listeners() {
        observeState()
    }

    private fun setUpCategoriesAdapter() {
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = categoriesAdapter
            setPadding(12.dp, 0, 12.dp, 0)
            addItemDecoration(
                InnerGridSpacingItemDecoration(
                    spanCount = 2,
                    horizontalSpacing = 8.dp,
                    verticalSpacing = 8.dp,
                )
            )
        }
    }


    private fun observeState() {
        lifecycleCollectLatest(viewModel.state) { state ->
            categoriesAdapter.submitList(state.categories)
        }
    }

    companion object {
        const val REQUEST_KEY_CATEGORY = "requestKeyCategory"
        const val BUNDLE_KEY_CATEGORY = "bundleKeyCategory"
    }

}