package com.example.academy_tbc

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.UserManagementFragment.Companion.OPERATION_ADD
import com.example.academy_tbc.UserManagementFragment.Companion.OPERATION_UPDATE
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentUserDetailsBinding
import com.example.academy_tbc.extensions.toText
import com.example.academy_tbc.utils.Validations.isValidEmail
import com.example.academy_tbc.utils.Validations.isValidInput


class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>(
    FragmentUserDetailsBinding::inflate
) {
    override fun bind() {

    }

    override fun listeners() {
        setFragmentResultListener("operation_request") { requestKey, bundle ->
            val operationResult = bundle.getString("operation_result")
            showButtonsBasedOnOperation(operationResult)
        }
        uiInitialization()
        addUser()
        updateUser()
        removeUser()
    }

    private fun uiInitialization() {
        initializeUpdateFields()
    }

    private fun addUser() = with(binding) {
        btnAddUser.setOnClickListener {
            val firstName: String = etFirstName.toText()
            val lastName = etLastName.toText()
            val age = etAge.text.toString().toIntOrNull()
            val email = etEmail.toText().lowercase()

            if (!(isValidEmail(
                    context = requireContext(), etEmail = etEmail, email = email
                ) && isValidInput(
                    context = requireContext(),
                    etFirstName = etFirstName,
                    etLastName = etLastName,
                    etAge = etAge,
                    firstName = firstName,
                    lastName = lastName,
                    age = age
                ))
            ) {
                return@setOnClickListener
            }

            val userItem = UserItem(firstName, lastName, age, email)
            setFragmentResult("request_add", bundleOf("newUser" to userItem))
            findNavController().popBackStack()
        }
    }

    private fun updateUser() = with(binding) {
        btnUpdateUser.setOnClickListener {
            val firstName: String = etFirstName.toText()
            val lastName = etLastName.toText()
            val age = etAge.text.toString().toIntOrNull()
            val email = etEmail.toText()

            if (!isValidInput(
                    context = requireContext(),
                    etFirstName = etFirstName,
                    etLastName = etLastName,
                    etAge = etAge,
                    firstName = firstName,
                    lastName = lastName,
                    age = age
                )
            ) {
                return@setOnClickListener
            }

            val updatedUser = UserItem(
                firstName = firstName, lastName = lastName, age = age, email = email
            )
            setFragmentResult("updated_user_request", bundleOf("updated_user" to updatedUser))
            findNavController().popBackStack()
        }
    }

    private fun removeUser() {
        binding.btnRemoveUser.setOnClickListener {
            val userEmail = binding.etEmail.toText()
            setFragmentResult("remove_user_request", bundleOf("user_email_to_remove" to userEmail))
            findNavController().popBackStack()
        }
    }

    private fun initializeUpdateFields() = with(binding) {
        setFragmentResultListener("update_request") { requestKey, bundle ->
            val user = bundle.getParcelable<UserItem>("userToUpdate")

            if (user != null) {
                etFirstName.setText(user.firstName)
                etLastName.setText(user.lastName)
                etAge.setText(user.age.toString())
                etEmail.setText(user.email)
                etEmail.isEnabled = false
                etEmail.setTextColor(resources.getColor(R.color.disabled_gray, null))
            }
        }
    }

    private fun showButtonsBasedOnOperation(operation: String?) = with(binding) {
        when (operation) {
            OPERATION_UPDATE -> {
                btnAddUser.visibility = View.GONE
                btnRemoveUser.visibility = View.VISIBLE
                btnUpdateUser.visibility = View.VISIBLE
            }

            OPERATION_ADD -> {
                btnAddUser.visibility = View.VISIBLE
                btnRemoveUser.visibility = View.GONE
                btnUpdateUser.visibility = View.GONE
            }
        }
    }
}