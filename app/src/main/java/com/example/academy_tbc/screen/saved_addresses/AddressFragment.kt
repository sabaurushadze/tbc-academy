package com.example.academy_tbc.screen.saved_addresses

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentAddressBinding
import com.example.academy_tbc.screen.address_form.AddressFormFragment.Companion.BUNDLE_KEY_EDIT_ADDRESS_FORM
import com.example.academy_tbc.screen.address_form.AddressFormFragment.Companion.BUNDLE_KEY_SAVE_ADDRESS_FORM
import com.example.academy_tbc.screen.address_form.AddressFormFragment.Companion.REQ_KEY_EDIT_ADDRESS_FORM
import com.example.academy_tbc.screen.address_form.AddressFormFragment.Companion.REQ_KEY_SAVE_ADDRESS_FORM
import java.util.UUID

class AddressFragment : BaseFragment<FragmentAddressBinding>(
    FragmentAddressBinding::inflate
) {
    private val savedAddresses: MutableList<AddressItem> = mutableListOf()

    private val addressAdapter by lazy {
        AddressAdapter(
            onClick = { id ->
                editAddress(id)
            },
            onAddressDelete = { id ->
                removeAddress(id)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQ_KEY_SAVE_ADDRESS_FORM) { key, bundle ->
            val addressItem = bundle.getParcelable<AddressItem>(BUNDLE_KEY_SAVE_ADDRESS_FORM)
            addressItem?.let {
                savedAddresses.add(0, it)
                addressAdapter.submitList(savedAddresses.toList()) {
                    binding.rvSavedAddresses.scrollToPosition(0)
                }
            }
            parentFragmentManager.clearFragmentResult(key)
        }
        setFragmentResultListener(REQ_KEY_EDIT_ADDRESS_FORM) { key, bundle ->
            val updatedItem = bundle.getParcelable<AddressItem>(BUNDLE_KEY_EDIT_ADDRESS_FORM)
            updatedItem?.let { newItem ->
                val index = savedAddresses.indexOfFirst { it.id == newItem.id }
                savedAddresses[index] = newItem
                addressAdapter.submitList(savedAddresses.toList()) {
                    binding.rvSavedAddresses.scrollToPosition(0)
                }
            }
            parentFragmentManager.clearFragmentResult(key)
        }
    }

    override fun bind() {
        binding.rvSavedAddresses.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvSavedAddresses.adapter = addressAdapter

        addressAdapter.submitList(savedAddresses)

    }

    override fun listeners() {
        saveAndNavigateToAddressForm()
    }

    private fun editAddress(id: UUID) = with(binding) {
        val addressToUpdate = savedAddresses.find { it.id == id }
        setFragmentResult(
            REQ_KEY_EDIT_ADDRESS, bundleOf(BUNDLE_KEY_EDIT_ADDRESS to addressToUpdate)
        )
        setFragmentResult(REQ_KEY_OPERATION_ADDRESS, bundleOf(BUNDLE_KEY_OPERATION_ADDRESS to EDIT))
        findNavController().navigate(
            AddressFragmentDirections.actionAddressFragmentToAddressFormFragment()
        )
    }

    private fun removeAddress(id: UUID) {
        savedAddresses.removeAll { it.id == id }
        addressAdapter.submitList(savedAddresses.toList()) {
            binding.rvSavedAddresses.scrollToPosition(0)
        }
    }

    private fun saveAndNavigateToAddressForm() = with(binding) {
        btnAddOrEditAddress.setOnClickListener {
            setFragmentResult(
                REQ_KEY_OPERATION_ADDRESS, bundleOf(BUNDLE_KEY_OPERATION_ADDRESS to SAVE)
            )
            findNavController().navigate(
                AddressFragmentDirections.actionAddressFragmentToAddressFormFragment()
            )
        }
    }

    companion object {
        const val REQ_KEY_OPERATION_ADDRESS = "req_key_operation_address"
        const val REQ_KEY_EDIT_ADDRESS = "req_key_edit_address"

        const val BUNDLE_KEY_OPERATION_ADDRESS = "bundle_key_operation_address"
        const val BUNDLE_KEY_EDIT_ADDRESS = "bundle_key_edit_address"

        const val SAVE = "save"
        const val EDIT = "edit"
    }
}