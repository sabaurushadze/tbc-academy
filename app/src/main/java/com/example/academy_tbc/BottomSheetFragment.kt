package com.example.academy_tbc

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.academy_tbc.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class BottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrdersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSubmit.setOnClickListener {
            if (binding.ratingBar.rating == 0f) {
                return@setOnClickListener
            }
            if (binding.etReview.text.isNullOrBlank()) {
                return@setOnClickListener
            }

            viewModel.markOrderReviewed()
            dismiss()

        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedOrder.collect { order ->
                order?.let {
                    binding.imgProduct.setImageResource(it.image)
                    binding.tvProductName.text = it.title
                    binding.tvProductColor.text = it.productColorName
                    binding.tvQuantity.text =
                        binding.root.context.getString(R.string.quantity, order.quantity)
                    binding.tvStatus.text = it.status.name
                    binding.tvPrice.text = binding.root.context.getString(R.string.price, it.price)

                    val bg = binding.vProductColor.background as GradientDrawable
                    bg.setColor(resources.getColor(it.productColor, null))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}