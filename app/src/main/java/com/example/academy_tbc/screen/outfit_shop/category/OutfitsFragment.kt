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
        CategoryAdapter { category ->
            onCategoryClick(category)
        }
    }

    private val outfitsAdapter by lazy {
        OutfitAdapter()
    }


    companion object {
        val outfits = mutableListOf<OutfitItem>(
            OutfitItem(
                id = UUID.randomUUID(),
                image = R.drawable.ryan_gosling,
                title = "Gucci suit",
                price = 795,
                category = "Camping"
            ),
            OutfitItem(
                id = UUID.randomUUID(),
                image = R.drawable.woman1,
                title = "Belt suit blazer",
                price = 120,
                category = "Party"
            ),
            OutfitItem(
                id = UUID.randomUUID(),
                image = R.drawable.woman2,
                title = "Belt suit blazer",
                price = 120,
                category = "Category3"
            ),
            OutfitItem(
                id = UUID.randomUUID(),
                image = R.drawable.vin_diesel,
                title = "Family special",
                price = 999,
                category = "Camping"
            ),
            OutfitItem(
                id = UUID.randomUUID(),
                image = R.drawable.woman3,
                title = "Belt suit blazer",
                price = 120,
                category = "Category1"
            ),
            OutfitItem(
                id = UUID.randomUUID(),
                image = R.drawable.woman4,
                title = "Belt suit blazer",
                price = 120,
                category = "Category2"
            ),
        )
        val categories = mutableListOf<CategoryItem>(
            CategoryItem(
                id = UUID.randomUUID(), title = "All"
            ),
            CategoryItem(
                id = UUID.randomUUID(), title = "\uD83C\uDF89   Party"
            ),
            CategoryItem(
                id = UUID.randomUUID(), title = "\uD83C\uDFD5   Camping"
            ),
            CategoryItem(
                id = UUID.randomUUID(), title = "Category1"
            ),
            CategoryItem(
                id = UUID.randomUUID(), title = "Category2"
            ),
            CategoryItem(
                id = UUID.randomUUID(), title = "Category3"
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

        rvCategory.addItemDecoration(
            MarginItemDecoration(
                defaultSpace = 10.dpToPx(
                    requireContext()
                ), lastItemSpace = 27.dpToPx(
                    requireContext()
                )
            )
        )
        categoryAdapter.submitList(categories.toList())
    }

    private fun onCategoryClick(category: CategoryItem) {
        val filteredCategory =
            category.title.filter { it.isLetterOrDigit() || it.isWhitespace() }.trimStart()
        val filteredOutfits = if (filteredCategory == "All") {
            outfits.toList()
        } else {
            outfits.filter { it.category == filteredCategory }
        }
        outfitsAdapter.submitList(filteredOutfits)
    }
}