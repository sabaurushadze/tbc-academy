package com.example.academy_tbc.screen.outfit_shop.category

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.R
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.OutfitsFragmentBinding
import com.example.academy_tbc.extension.dpToPx
import com.example.academy_tbc.screen.outfit_shop.outfits.OutfitAdapter
import com.example.academy_tbc.screen.outfit_shop.outfits.OutfitItem
import com.example.academy_tbc.utils.GridSpacingItemDecoration
import com.example.academy_tbc.utils.MarginItemDecoration
import java.util.UUID

class OutfitsFragment : BaseFragment<OutfitsFragmentBinding>(
    OutfitsFragmentBinding::inflate
) {
    private val categoryAdapter by lazy {
        CategoryAdapter() {
            onCategoryClick()
        }
    }

    private val outfitsAdapter by lazy {
        OutfitAdapter()
    }


    companion object {
        val outfits = mutableListOf<OutfitItem>(
            OutfitItem(
                id = UUID.randomUUID(),
                image = R.drawable.woman1,
                title = "Belt suit blazer",
                price = 120,
            ),
            OutfitItem(
                id = UUID.randomUUID(),
                image = R.drawable.woman2,
                title = "Belt suit blazer",
                price = 120,
            ),
            OutfitItem(
                id = UUID.randomUUID(),
                image = R.drawable.woman3,
                title = "Belt suit blazer",
                price = 120,
            ),
            OutfitItem(
                id = UUID.randomUUID(),
                image = R.drawable.woman4,
                title = "Belt suit blazer",
                price = 120,
            ),
        )
        val categories = mutableListOf<CategoryItem>(
            CategoryItem(
                id = UUID.randomUUID(),
                title = "All"
            ),
            CategoryItem(
                id = UUID.randomUUID(),
                title = "\uD83C\uDFD5   Party"
            ),
            CategoryItem(
                id = UUID.randomUUID(),
                title = "Camping"
            ),
            CategoryItem(
                id = UUID.randomUUID(),
                title = "Category1"
            ),
            CategoryItem(
                id = UUID.randomUUID(),
                title = "Category2"
            ),
            CategoryItem(
                id = UUID.randomUUID(),
                title = "Category3"
            ),
        )
    }

    override fun bind() = with(binding) {
        rvCategory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCategory.adapter = categoryAdapter

        rvOutfits.layoutManager = GridLayoutManager(context, 2)
        rvOutfits.adapter = outfitsAdapter


        rvOutfits.addItemDecoration(
            GridSpacingItemDecoration(
                2, 19.dpToPx(requireContext()), 23.dpToPx(requireContext())
            )
        )
        outfitsAdapter.submitList(outfits.toList())

        rvCategory.addItemDecoration(MarginItemDecoration(10.dpToPx(requireContext())))
        categoryAdapter.submitList(categories.toList())
    }

    private fun onCategoryClick() {
    }

    override fun listeners() {

    }


}