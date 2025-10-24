package com.example.academy_tbc.screen.address_form

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.R
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentAddressFormBinding
import com.example.academy_tbc.screen.saved_addresses.AddressFragment.Companion.BUNDLE_KEY_EDIT_ADDRESS
import com.example.academy_tbc.screen.saved_addresses.AddressFragment.Companion.BUNDLE_KEY_OPERATION_ADDRESS
import com.example.academy_tbc.screen.saved_addresses.AddressFragment.Companion.EDIT
import com.example.academy_tbc.screen.saved_addresses.AddressFragment.Companion.REQ_KEY_EDIT_ADDRESS
import com.example.academy_tbc.screen.saved_addresses.AddressFragment.Companion.REQ_KEY_OPERATION_ADDRESS
import com.example.academy_tbc.screen.saved_addresses.AddressFragment.Companion.SAVE
import com.example.academy_tbc.screen.saved_addresses.AddressIcon
import com.example.academy_tbc.screen.saved_addresses.AddressItem
import com.example.academy_tbc.screen.saved_addresses.AddressType
import com.example.academy_tbc.utils.Validations
import java.util.UUID

class AddressFormFragment : BaseFragment<FragmentAddressFormBinding>(
    FragmentAddressFormBinding::inflate
) {
    private var operationType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQ_KEY_OPERATION_ADDRESS) { _, bundle ->
            operationType = bundle.getString(BUNDLE_KEY_OPERATION_ADDRESS)
            changeButtonText()
            addAddressAndNavigateToAddresses()
        }

        setFragmentResultListener(REQ_KEY_EDIT_ADDRESS) { key, bundle ->
            val addressItemToUpdate = bundle.getParcelable<AddressItem>(BUNDLE_KEY_EDIT_ADDRESS)
            addressItemToUpdate?.let {
                binding.autoCompleteAddressType.setText(addressItemToUpdate.addressType, false)
                binding.etAddressType.setText(addressItemToUpdate.addressType)
                binding.etAddress.setText(addressItemToUpdate.address)

                editAddressAndNavigateToAddresses(addressItemToUpdate)
            }
            parentFragmentManager.clearFragmentResult(key)
        }
    }

    override fun bind() {
        val addressTypes = AddressType.entries
        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line, addressTypes
        )
        binding.autoCompleteAddressType.setAdapter(adapter)
        addAddressAndNavigateToAddresses()
    }


    private fun editAddressAndNavigateToAddresses(addressItemToUpdate: AddressItem) =
        with(binding) {
            btnAddOrEditAddress.setOnClickListener {
                val addressTypeText = autoCompleteAddressType.text.toString()
                val addressType = etAddressType.text.toString()
                val address = etAddress.text.toString()

                if (operationType == EDIT) {
                    if (Validations.validateInputs(
                            context = requireContext(),
                            view = root,
                            addressTypeDropdown = addressTypeText,
                            addressType = addressType,
                            address = address,
                            etAddress = etAddress,
                            etName = etAddressType,
                        )
                    ) {
                        val selectedIcon = addressToIcon(addressTypeText)

                        val newAddressItem = addressItemToUpdate.copy(
                            addressType = addressType, address = address, icon = selectedIcon
                        )
                        setFragmentResult(
                            REQ_KEY_EDIT_ADDRESS_FORM,
                            bundleOf(BUNDLE_KEY_EDIT_ADDRESS_FORM to newAddressItem)
                        )
                        findNavController().popBackStack()
                    }
                }
            }
        }

    private fun addAddressAndNavigateToAddresses() = with(binding) {
        btnAddOrEditAddress.setOnClickListener {
            val addressTypeText = autoCompleteAddressType.text.toString()
            val addressType = etAddressType.text.toString()
            val address = etAddress.text.toString()

            if (operationType == SAVE) {
                if (Validations.validateInputs(
                        context = requireContext(),
                        view = root,
                        addressType = addressType,
                        addressTypeDropdown = addressTypeText,
                        address = address,
                        etAddress = etAddress,
                        etName = etAddressType,
                    )
                ) {
                    val selectedIcon = addressToIcon(addressTypeText)

                    val addressItem = AddressItem(
                        id = UUID.randomUUID(),
                        addressType = addressType,
                        address = address,
                        icon = selectedIcon
                    )

                    setFragmentResult(
                        REQ_KEY_SAVE_ADDRESS_FORM,
                        bundleOf(BUNDLE_KEY_SAVE_ADDRESS_FORM to addressItem)
                    )
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun addressToIcon(
        addressTypeDropdown: String,
    ): AddressIcon {
        return when (addressTypeDropdown.lowercase()) {
            HOME -> AddressIcon.HOME
            OFFICE -> AddressIcon.OFFICE
            else -> AddressIcon.HOME
        }
    }

    private fun changeButtonText() {
        if (operationType == EDIT) {

            binding.btnAddOrEditAddress.text = getString(R.string.btn_edit)
        }
    }

    companion object {
        const val REQ_KEY_SAVE_ADDRESS_FORM = "req_key_save_address_form"
        const val REQ_KEY_EDIT_ADDRESS_FORM = "req_key_edit_address_form"

        const val BUNDLE_KEY_SAVE_ADDRESS_FORM = "bundle_key_save_address_form"
        const val BUNDLE_KEY_EDIT_ADDRESS_FORM = "bundle_key_edit_address_form"

        const val HOME = "home"
        const val OFFICE = "office"
    }
}