package com.example.academy_tbc.screen.card_management

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentCardManagementBinding
import com.example.academy_tbc.viewmodel.CardViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CardManagementFragment : BaseFragment<FragmentCardManagementBinding>(
    FragmentCardManagementBinding::inflate
) {
    private val viewModel: CardViewModel by activityViewModels()
    private val cardAdapter by lazy {
        CardAdapter { id ->
            setFragmentResult(REQUEST_KEY_ID, bundleOf(BUNDLE_KEY_ID to id))
            BottomSheetFragment().show(parentFragmentManager, "")
        }
    }

    override fun bind() {
        binding.pager.adapter = cardAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    cardAdapter.submitList(state.cardList)
                }
            }
        }
    }

    override fun listeners() {
        navigateToAddNewCard()
    }

    private fun navigateToAddNewCard() {
        binding.tvAddNew.setOnClickListener {
            findNavController().navigate(CardManagementFragmentDirections.actionCardManagementFragmentToNewCardFragment())
        }
    }

    companion object {
        const val REQUEST_KEY_ID = "requestKeyId"
        const val BUNDLE_KEY_ID = "bundleKeyId"
    }
}