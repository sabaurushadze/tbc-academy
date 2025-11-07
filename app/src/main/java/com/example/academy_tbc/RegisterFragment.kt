package com.example.academy_tbc

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.box.BoxAdapter
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentRegisterBinding
import com.example.academy_tbc.extension.showSnackBar
import com.example.academy_tbc.field.FieldsViewModel
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val fieldsViewModel: FieldsViewModel by viewModels()
    private var boxAdapter: BoxAdapter? = null

    override fun bind() {
        binding.rvContainer.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                fieldsViewModel.fieldContainers.collect { containers ->
                    boxAdapter = BoxAdapter(containers, fieldsViewModel)
                    binding.rvContainer.adapter = boxAdapter
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            onRegisterClicked()
        }
    }

    private fun onRegisterClicked() {
        val adapter = boxAdapter ?: return
        val values = fieldsViewModel.fieldValues.value
        val allFields = adapter.getAllFields()

        var hasError = false

        allFields.reversed().forEach { field ->
            val value = values[field.fieldId]
            if (field.required && value.isNullOrEmpty()) {
                hasError = true
                binding.root.showSnackBar(
                    getString(R.string.field_not_filled_in, field.hint)
                )
            }
        }

        if (!hasError) {
            binding.root.showSnackBar(
                getString(R.string.registered_succsessfully)
            )
        }
    }
}



