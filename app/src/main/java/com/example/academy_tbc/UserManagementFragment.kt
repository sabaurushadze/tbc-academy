package com.example.academy_tbc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.UserManager.activeUsers
import com.example.academy_tbc.UserManager.deletedUserCount
import com.example.academy_tbc.databinding.FragmentUserManagementBinding
import com.example.academy_tbc.extensions.showSnackBar


class UserManagementFragment : Fragment() {
    private var _binding: FragmentUserManagementBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserManagementBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listeners() {
        addUser()
        updateUser()
        showResult()
    }

    private fun addUser() {
        binding.btnAddUser.setOnClickListener {
            findNavController().navigate(
                UserManagementFragmentDirections.actionUserManagementFragmentToUserDetailsFragment(
                    operation = OPERATION_ADD, email = ""
                )
            )
        }
    }

    private fun updateUser() {
        binding.btnUpdateUser.setOnClickListener {
            if (activeUsers > 0) {
                val randomUserEmail = UserManager.getRandomUser()
                findNavController().navigate(
                    UserManagementFragmentDirections.actionUserManagementFragmentToUserDetailsFragment(
                        operation = OPERATION_UPDATE, email = randomUserEmail
                    )
                )
            } else {
                UserManager.setOperation(Operation.FAILURE)
                showResult()
                binding.root.showSnackBar(getString(R.string.error_no_users_found))
            }
        }
    }

    private fun showResult() = with(binding) {
        tvActiveUserCount.text = getString(R.string.active_users, activeUsers)
        tvDeletedUserCount.text = getString(R.string.deleted_users, deletedUserCount)
        when (UserManager.currentOperation) {
            Operation.PENDING -> setTextToSuccessOrFailure(
                tvOperationSuccessOrError, R.string.pending, R.color.info_blue
            )

            Operation.SUCCESS -> setTextToSuccessOrFailure(
                tvOperationSuccessOrError, R.string.success, R.color.success_green
            )

            Operation.FAILURE -> setTextToSuccessOrFailure(
                tvOperationSuccessOrError, R.string.failure, R.color.error_red
            )
        }
    }

    private fun setTextToSuccessOrFailure(
        tvSuccessOrError: TextView,
        textId: Int,
        colorId: Int,
    ) {
        tvSuccessOrError.text = getString(textId)
        tvSuccessOrError.setTextColor(requireContext().getColor(colorId))
    }

    companion object {
        const val OPERATION_ADD = "add"
        const val OPERATION_UPDATE = "update"
    }
}