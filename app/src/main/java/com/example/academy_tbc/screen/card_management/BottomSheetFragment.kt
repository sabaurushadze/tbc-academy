package com.example.academy_tbc.screen.card_management

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.example.academy_tbc.common.BaseBottomSheetDialogFragment
import com.example.academy_tbc.databinding.FragmentBottomSheetBinding
import com.example.academy_tbc.screen.card_management.CardManagementFragment.Companion.BUNDLE_KEY_ID
import com.example.academy_tbc.screen.card_management.CardManagementFragment.Companion.REQUEST_KEY_ID
import com.example.academy_tbc.viewmodel.CardViewModel

class BottomSheetFragment : BaseBottomSheetDialogFragment<FragmentBottomSheetBinding>(
    FragmentBottomSheetBinding::inflate
) {
    private val viewModel: CardViewModel by activityViewModels()
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_KEY_ID) { requestKey, bundle ->
            id = bundle.getString(BUNDLE_KEY_ID)
        }
    }

    override fun listeners() {
        binding.btnNo.setOnClickListener {
            dismiss()
        }
        binding.btnYes.setOnClickListener {
            dismiss()
            viewModel.deleteCard(id)
        }
    }
}