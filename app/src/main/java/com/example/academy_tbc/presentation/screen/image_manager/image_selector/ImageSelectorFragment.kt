package com.example.academy_tbc.presentation.screen.image_manager.image_selector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentImageSelectorBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.loadImage
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.image_manager.image_action.ImageActionBottomSheetFragment
import com.example.academy_tbc.presentation.screen.image_manager.image_action.enums.ImageAction
import com.example.academy_tbc.presentation.util.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ImageSelectorFragment : BaseFragment<FragmentImageSelectorBinding>(
    FragmentImageSelectorBinding::inflate
) {
    private val viewModel: ImageManagerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(ImageActionBottomSheetFragment.FRAGMENT_REQUEST_KEY) { _, bundle ->
            val itemName = bundle.getString(ImageActionBottomSheetFragment.SELECTED_ITEM_KEY)
            itemName?.let {
                val item = ImageAction.valueOf(itemName)
                handleItemAction(item)
            }

        }
    }


    override fun listeners() {
        onSelectImageClick()
        onUploadImageClick()
        observeState()
        observeSideEffects()
    }

    private val imageSelectListener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let {
                    viewModel.onEvent(ImageSelectorEvent.OnUriCreated(it))
                    viewModel.onEvent(ImageSelectorEvent.ProcessImage)
                }

            }
        }

    private val cameraResultListener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.onEvent(ImageSelectorEvent.ProcessImage)
            }
        }

    private fun handleItemAction(item: ImageAction) {
        when (item) {
            ImageAction.SELECT_FROM_LOCALE -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                imageSelectListener.launch(intent)
            }

            ImageAction.TAKE_PHOTO -> {
                openCamera()
            }
        }

    }


    private fun onSelectImageClick() {
        binding.btnSelectImage.setOnClickListener {
            val bottomSheet = ImageActionBottomSheetFragment()
            bottomSheet.show(parentFragmentManager, IMAGE_ACTION_BOTTOM_SHEET)
        }
    }

    private fun onUploadImageClick() {
        binding.btnUploadImg.setOnClickListener {
            viewModel.onEvent(ImageSelectorEvent.UploadImage)
        }
    }


    private fun observeSideEffects() = with(binding) {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                ImageSelectorSideEffect.SuccessfullyUploaded -> {
                    root.showSnackBar(getString(R.string.image_uploaded_successfully))
                }

                ImageSelectorSideEffect.UploadFailed -> {
                    root.showSnackBar(getString(R.string.image_upload_failed))
                }
            }
        }
    }

    private fun observeState() {
        lifecycleCollectLatest(viewModel.state) { state ->
            handleLoading(state.uploading)

            state.compressedByteArray?.let {
                binding.ivSelectedImage.loadImage(it.byteArray)
            }
        }
    }

    private fun handleLoading(uploading: Boolean) {
        binding.btnSelectImage.isEnabled = !uploading
        binding.btnUploadImg.isEnabled = !uploading
        binding.itemLoader.root.isVisible = uploading
    }

    private fun createImageFile(): File {
        val timeStamp = DateUtils.getTimeStamp()
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    private fun openCamera() {
        val photoFile = createImageFile()
        val photoUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            photoFile
        )

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        }
        viewModel.onEvent(ImageSelectorEvent.OnUriCreated(photoUri))
        cameraResultListener.launch(cameraIntent)
    }

    companion object {
        const val IMAGE_ACTION_BOTTOM_SHEET = "image_action_bottom_sheet"
    }
}