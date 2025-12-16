package com.example.academy_tbc.presentation.screen.part_detail

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentPartDetailBinding
import com.example.academy_tbc.presentation.common.view.BaseDialogFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.show
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.home.HomeFragment.Companion.BUNDLE_KEY_ID
import com.example.academy_tbc.presentation.screen.home.HomeFragment.Companion.REQUEST_KEY_ID
import com.example.academy_tbc.presentation.screen.part_detail.adapter.ItemDetailsAdapter
import com.example.academy_tbc.presentation.screen.part_detail.adapter.PartDetailsAdapter
import com.example.academy_tbc.presentation.screen.part_detail.model.ImageUi
import com.example.academy_tbc.presentation.screen.part_detail.mapper.getItemDetailsByCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PartDetailFragment : BaseDialogFragment<FragmentPartDetailBinding>(
    FragmentPartDetailBinding::inflate
) {
    private val viewModel: PartDetailViewModel by viewModels()
    val viewPagerImageAdapter by lazy { PartDetailsAdapter() }
    val itemDetailsAdapter by lazy { ItemDetailsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_TranslucentDecor)

        setFragmentResultListener(REQUEST_KEY_ID) { _, bundle ->
            val partId = bundle.getInt(BUNDLE_KEY_ID)
            viewModel.onEvent(PartDetailEvent.GetPartDetails(partId))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (savedInstanceState == null) {
            dialog.window?.setWindowAnimations(R.style.DialogSlideAnimation)
        }
        return dialog
    }

    override fun bind() {
        binding.viewPagerParts.adapter = viewPagerImageAdapter
        setUpItemDetailsAdapter()
    }

    private fun setUpItemDetailsAdapter() {
        binding.rvItemDetails.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(context)
            adapter = itemDetailsAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun listeners() {
        observeSideEffects()
        observeState()
        setupImageCounter()
        goBackToHomeFragment()
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

    private fun observeState() = with(binding) {
        lifecycleCollect(viewModel.state) { state ->
            val imagesUi = state.partDetails?.images?.mapIndexed { index, url ->
                ImageUi(id = index, url = url)
            }
            viewPagerImageAdapter.submitList(imagesUi) {
                val total = viewPagerImageAdapter.itemCount
                if (total > 0) {
                    tvImageCounter.show()
                    tvImageCounter.text =
                        getString(R.string.current_image_position, 1, total)
                }
            }
            state.partDetails?.let { part ->
                tvItemTitle.text = part.title
                tvPrice.text = getString(R.string.price, part.price)
                tvCondition.text = getString(part.condition)

                val itemDetails = getItemDetailsByCategory(part)
                itemDetailsAdapter.submitList(itemDetails)
            }

        }
    }

    private fun goBackToHomeFragment() {
        binding.btnBack.setOnClickListener {
            dismiss()
        }
    }




}