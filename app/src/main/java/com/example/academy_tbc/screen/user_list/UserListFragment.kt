package com.example.academy_tbc.screen.user_list

import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.R
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentUserListBinding
import com.example.academy_tbc.extension.showSnackBar

class UserListFragment : BaseFragment<FragmentUserListBinding>(
    FragmentUserListBinding::inflate
) {
    private val users = mutableListOf<UserItem>()
    private var usersRemoved = 0

    override fun listeners() {
        showResult()
        addUser()
        handleAddUser()
        handleUpdateUser()
        handleRemoveUser()
    }

    override fun bind() = with(binding) {
        rvUsers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvUsers.adapter = UsersAdapter(
            users = users,
        ) { clickedUser ->
            updateUser(clickedUser)
        }
    }

    private fun handleAddUser() = with(binding) {
        setFragmentResultListener("request_add") { requestKey, bundle ->
            val newUser = bundle.getParcelable<UserItem>("newUser")
            if (newUser != null) {
                val isEmailPresent = users.any { it.email == newUser.email }

                if (isEmailPresent) {
                    setTextToSuccessOrFailure(
                        tvSuccessOrError = tvOperationSuccessOrError,
                        textId = R.string.failure,
                        colorId = R.color.error_red
                    )
                    root.showSnackBar(getString(R.string.user_already_exists))
                } else {
                    users.add(newUser)
                    rvUsers.adapter?.notifyItemInserted(users.size - 1)
                    showResult()
                    setTextToSuccessOrFailure(
                        tvSuccessOrError = tvOperationSuccessOrError,
                        textId = R.string.success,
                        colorId = R.color.success_green
                    )
                    root.showSnackBar(getString(R.string.user_added_successfully))
                }
            }
        }
    }

    private fun handleUpdateUser() = with(binding) {
        setFragmentResultListener("updated_user_request") { requestKey, bundle ->
            val updatedUser = bundle.getParcelable<UserItem>("updated_user")
            if (updatedUser != null) {
                val userIndex = users.indexOfFirst { it.email == updatedUser.email }

                if (userIndex != -1) {
                    users[userIndex] = users[userIndex].copy(
                        firstName = updatedUser.firstName,
                        lastName = updatedUser.lastName,
                        age = updatedUser.age
                    )
                    setTextToSuccessOrFailure(
                        tvSuccessOrError = tvOperationSuccessOrError,
                        textId = R.string.success,
                        colorId = R.color.success_green
                    )
                    root.showSnackBar(getString(R.string.user_updated_successfully))
                    rvUsers.adapter?.notifyItemChanged(userIndex)
                } else {
                    setTextToSuccessOrFailure(
                        tvSuccessOrError = tvOperationSuccessOrError,
                        textId = R.string.failure,
                        colorId = R.color.error_red
                    )
                    root.showSnackBar(getString(R.string.user_update_failed))
                }
            }
            showResult()
        }
    }

    private fun handleRemoveUser() = with(binding) {
        setFragmentResultListener("remove_user_request") { requestKey, bundle ->
            val userEmail = bundle.getString("user_email_to_remove")
            val userIndex = users.indexOfFirst { it.email == userEmail }

            if (userIndex != -1) {
                users.removeAt(userIndex)
                rvUsers.adapter?.notifyItemRemoved(userIndex)
                usersRemoved++
                setTextToSuccessOrFailure(
                    tvSuccessOrError = tvOperationSuccessOrError,
                    textId = R.string.success,
                    colorId = R.color.success_green
                )
                root.showSnackBar(getString(R.string.user_deleted_successfully))
            } else {
                setTextToSuccessOrFailure(
                    tvSuccessOrError = tvOperationSuccessOrError,
                    textId = R.string.success,
                    colorId = R.color.success_green
                )
                root.showSnackBar(getString(R.string.user_deletion_failed))
            }
            showResult()
        }
    }

    private fun addUser() {
        binding.btnAddUser.setOnClickListener {
            setFragmentResult("operation_request", bundleOf("operation_result" to OPERATION_ADD))
            findNavController().navigate(
                UserListFragmentDirections.actionUserManagementFragmentToUserDetailsFragment()
            )
        }
    }

    private fun updateUser(clickedUser: UserItem) {
        setFragmentResult("operation_request", bundleOf("operation_result" to OPERATION_UPDATE))
        setFragmentResult("update_request", bundleOf("userToUpdate" to clickedUser))
        findNavController().navigate(
            UserListFragmentDirections.actionUserManagementFragmentToUserDetailsFragment()
        )
    }


    private fun showResult() = with(binding) {
        tvActiveUserCount.text = getString(R.string.active_users, users.size)
        tvDeletedUserCount.text = getString(R.string.users_removed, usersRemoved)
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