package com.example.academy_tbc.presentation.screen.image_manager.image_action

import android.os.Bundle
import com.example.academy_tbc.databinding.FragmentBottomSheetImageActionBinding
import com.example.academy_tbc.presentation.common.view.BaseBottomSheet
import com.example.academy_tbc.presentation.screen.image_manager.image_action.enums.ImageAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageActionBottomSheetFragment : BaseBottomSheet<FragmentBottomSheetImageActionBinding>(
    FragmentBottomSheetImageActionBinding::inflate
) {

    override fun listeners() {
        onSelectFromLocaleClick()
        onTakePhotoClick()
    }

    private fun onSelectFromLocaleClick() {
        binding.btnSelectImage.setOnClickListener {
            sendResult(ImageAction.SELECT_FROM_LOCALE)
        }
    }

    private fun onTakePhotoClick() {
        binding.btnTakePicture.setOnClickListener {
            sendResult(ImageAction.TAKE_PHOTO)
        }
    }

    private fun sendResult(selected: ImageAction) {
        val data = Bundle().apply {
            putString(SELECTED_ITEM_KEY, selected.name)
        }
        parentFragmentManager.setFragmentResult(FRAGMENT_REQUEST_KEY, data)
        dismiss()
    }

    companion object {
        const val FRAGMENT_REQUEST_KEY = "request_key"
        const val SELECTED_ITEM_KEY = "selected_item_key"
    }

}