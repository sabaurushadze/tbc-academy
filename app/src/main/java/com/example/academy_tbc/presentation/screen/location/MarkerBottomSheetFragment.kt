package com.example.academy_tbc.presentation.screen.location

import androidx.navigation.fragment.navArgs
import com.example.academy_tbc.databinding.FragmentMarkerBottomSheetBinding
import com.example.academy_tbc.presentation.common.view.BaseBottomSheet
import com.example.academy_tbc.presentation.extension.loadImage

class MarkerBottomSheetFragment : BaseBottomSheet<FragmentMarkerBottomSheetBinding>(
    FragmentMarkerBottomSheetBinding::inflate
) {
    private val args: MarkerBottomSheetFragmentArgs  by navArgs()
    override fun listeners() = with(binding) {
        tvPlaceTitle.text = args.title
        tvAddress.text = args.address
        ivLocation.loadImage(args.image)
    }
}