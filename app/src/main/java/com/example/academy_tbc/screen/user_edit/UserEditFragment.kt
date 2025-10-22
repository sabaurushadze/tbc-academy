package com.example.academy_tbc.screen.user_edit

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.R
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentUserEditBinding
import com.example.academy_tbc.extension.toText
import com.example.academy_tbc.screen.user_list.UserItem
import com.example.academy_tbc.screen.user_list.UserListFragment
import com.example.academy_tbc.screen.user_list.UserListFragment.Companion.KEY_NEW_USER
import com.example.academy_tbc.screen.user_list.UserListFragment.Companion.KEY_OPERATION_RESULT
import com.example.academy_tbc.screen.user_list.UserListFragment.Companion.KEY_UPDATED_USER
import com.example.academy_tbc.screen.user_list.UserListFragment.Companion.KEY_USER_EMAIL_TO_REMOVE
import com.example.academy_tbc.screen.user_list.UserListFragment.Companion.KEY_USER_TO_UPDATE
import com.example.academy_tbc.screen.user_list.UserListFragment.Companion.REQUEST_ADD_USER
import com.example.academy_tbc.screen.user_list.UserListFragment.Companion.REQUEST_OPERATION
import com.example.academy_tbc.screen.user_list.UserListFragment.Companion.REQUEST_REMOVE_USER
import com.example.academy_tbc.screen.user_list.UserListFragment.Companion.REQUEST_UPDATED_USER
import com.example.academy_tbc.screen.user_list.UserListFragment.Companion.REQUEST_USER_TO_UPDATE
import com.example.academy_tbc.utils.Validations

class UserEditFragment : BaseFragment<FragmentUserEditBinding>(
    FragmentUserEditBinding::inflate
) {
    override fun listeners() {
        setFragmentResultListener(REQUEST_OPERATION) { requestKey, bundle ->
            val operationResult = bundle.getString(KEY_OPERATION_RESULT)
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

            if (!(Validations.isValidEmail(
                    context = requireContext(), etEmail = etEmail, email = email
                ) && Validations.isValidInput(
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
            setFragmentResult(REQUEST_ADD_USER, bundleOf(KEY_NEW_USER to userItem))
            findNavController().popBackStack()
        }
    }

    private fun updateUser() = with(binding) {
        btnUpdateUser.setOnClickListener {
            val firstName: String = etFirstName.toText()
            val lastName = etLastName.toText()
            val age = etAge.text.toString().toIntOrNull()
            val email = etEmail.toText()

            if (!Validations.isValidInput(
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
            setFragmentResult(REQUEST_UPDATED_USER, bundleOf(KEY_UPDATED_USER to updatedUser))
            findNavController().popBackStack()
        }
    }

    private fun removeUser() {
        binding.btnRemoveUser.setOnClickListener {
            val userEmail = binding.etEmail.toText()
            setFragmentResult(REQUEST_REMOVE_USER, bundleOf(KEY_USER_EMAIL_TO_REMOVE to userEmail))
            findNavController().popBackStack()
        }
    }

    private fun initializeUpdateFields() = with(binding) {
        setFragmentResultListener(REQUEST_USER_TO_UPDATE) { requestKey, bundle ->
            val user = bundle.getParcelable<UserItem>(KEY_USER_TO_UPDATE)

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
            UserListFragment.Companion.OPERATION_UPDATE -> {
                btnAddUser.visibility = View.GONE
                btnRemoveUser.visibility = View.VISIBLE
                btnUpdateUser.visibility = View.VISIBLE
            }

            UserListFragment.Companion.OPERATION_ADD -> {
                btnAddUser.visibility = View.VISIBLE
                btnRemoveUser.visibility = View.GONE
                btnUpdateUser.visibility = View.GONE
            }
        }
    }
}