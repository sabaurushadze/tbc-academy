package com.example.academy_tbc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.academy_tbc.UserManagementFragment.Companion.OPERATION_ADD
import com.example.academy_tbc.UserManagementFragment.Companion.OPERATION_UPDATE
import com.example.academy_tbc.databinding.FragmentUserDetailsBinding
import com.example.academy_tbc.extensions.showSnackBar
import com.example.academy_tbc.extensions.toText
import com.example.academy_tbc.utils.Validations.isValidEmail
import com.example.academy_tbc.utils.Validations.isValidInput


class UserDetailsFragment : Fragment() {
    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: UserDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiInitialization()
        listeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listeners() {
        addUser()
        updateUser(args.email)
        removeUser(args.email)
    }

    private fun uiInitialization() {
        showButtonsBasedOnOperation()
        initializeUpdateFields(args.email)
    }

    private fun addUser() = with(binding) {
        btnAddUser.setOnClickListener {
            val firstName: String = etFirstName.toText()
            val lastName = etLastName.toText()
            val age = etAge.toText()
            val email = etEmail.toText().lowercase()

            if (UserManager.containsEmail(email)) {
                root.showSnackBar(getString(R.string.user_already_exists))
                return@setOnClickListener
            }

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

            val user = User(firstName, lastName, age)
            UserManager.addUser(user = user, email = email)
            UserManager.setOperation(Operation.SUCCESS)
            findNavController().popBackStack()
        }
    }

    private fun updateUser(email: String) = with(binding) {
        btnUpdateUser.setOnClickListener {
            val firstName: String = etFirstName.toText()
            val lastName = etLastName.toText()
            val age = etAge.toText()


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

            val updatedUser = User(firstName, lastName, age)

            UserManager.updateUser(user = updatedUser, email = email)
            UserManager.setOperation(Operation.SUCCESS)
            findNavController().popBackStack()
        }
    }

    private fun removeUser(email: String) {
        binding.btnRemoveUser.setOnClickListener {
            UserManager.removeUser(email)
            UserManager.setOperation(Operation.SUCCESS)
            findNavController().popBackStack()
        }
    }

    private fun initializeUpdateFields(email: String) {
        val user = UserManager.getUserByEmail(email)
        if (user != null) {
            binding.etFirstName.setText(user.firstName)
            binding.etLastName.setText(user.lastName)
            binding.etAge.setText(user.age)
            binding.etEmail.setText(email)
            binding.etEmail.isEnabled = false
        }
    }

    private fun showButtonsBasedOnOperation() = with(binding) {
        when (args.operation) {
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