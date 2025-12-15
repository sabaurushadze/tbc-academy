package com.example.academy_tbc.presentation.screen.part_detail

import android.os.Bundle
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentPartDetailBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.show
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.home.HomeFragment.Companion.BUNDLE_KEY_ID
import com.example.academy_tbc.presentation.screen.home.HomeFragment.Companion.REQUEST_KEY_ID
import com.example.academy_tbc.presentation.screen.part_detail.adapter.PartDetailsAdapter
import com.example.academy_tbc.presentation.screen.part_detail.model.ImageUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PartDetailFragment : BaseFragment<FragmentPartDetailBinding>(
    FragmentPartDetailBinding::inflate
) {
    private val viewModel: PartDetailViewModel by viewModels()
    val viewPagerImageAdapter by lazy { PartDetailsAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_KEY_ID) { _, bundle ->
            val partId = bundle.getInt(BUNDLE_KEY_ID)
            viewModel.onEvent(PartDetailEvent.GetPartDetails(partId))
        }
    }

    override fun bind() {
        binding.viewPagerParts.adapter = viewPagerImageAdapter
    }

    override fun listeners() {
        observeSideEffects()
        observeState()
        setupImageCounter()
    }

    private fun observeSideEffects() {
        lifecycleCollect(viewModel.effect) { effect ->
            when (effect) {
                is PartDetailSideEffect.ShowError -> binding.root.showSnackBar(
                    effect.error.getString(
                        requireContext()
                    )
                )

            }
        }
    }

    private fun setupImageCounter() {
        binding.viewPagerParts.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val total = viewPagerImageAdapter.itemCount
                if (total > 0) {
                    binding.tvImageCounter.show()
                    binding.tvImageCounter.text =
                        getString(R.string.current_image_position, position + 1, total)
                }
            }
        })
    }

    private fun observeState() {
        lifecycleCollect(viewModel.state) { state ->
            val imagesUi = state.partDetails?.images?.mapIndexed { index, url ->
                ImageUi(id = index, url = url)
            }
            viewPagerImageAdapter.submitList(imagesUi) {
                val total = viewPagerImageAdapter.itemCount
                if (total > 0) {
                    binding.tvImageCounter.show()
                    binding.tvImageCounter.text =
                        getString(R.string.current_image_position, 1, total)
                }
            }
        }
    }


}