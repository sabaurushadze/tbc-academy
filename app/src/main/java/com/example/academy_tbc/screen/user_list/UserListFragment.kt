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
        setFragmentResultListener(REQUEST_ADD_USER) { requestKey, bundle ->
            val newUser = bundle.getParcelable<UserItem>(KEY_NEW_USER)
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
        setFragmentResultListener(REQUEST_UPDATED_USER) { requestKey, bundle ->
            val updatedUser = bundle.getParcelable<UserItem>(KEY_UPDATED_USER)
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
        setFragmentResultListener(REQUEST_REMOVE_USER) { requestKey, bundle ->
            val userEmail = bundle.getString(KEY_USER_EMAIL_TO_REMOVE)
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
            setFragmentResult(REQUEST_OPERATION, bundleOf(KEY_OPERATION_RESULT to OPERATION_ADD))
            findNavController().navigate(
                UserListFragmentDirections.actionUserManagementFragmentToUserDetailsFragment()
            )
        }
    }

    private fun updateUser(clickedUser: UserItem) {
        setFragmentResult(REQUEST_OPERATION, bundleOf(KEY_OPERATION_RESULT to OPERATION_UPDATE))
        setFragmentResult(REQUEST_USER_TO_UPDATE, bundleOf(KEY_USER_TO_UPDATE to clickedUser))
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
        const val REQUEST_OPERATION = "operation_request"
        const val REQUEST_ADD_USER = "request_add_user"
        const val REQUEST_USER_TO_UPDATE = "request_user_to_update"
        const val REQUEST_UPDATED_USER = "request_updated_user"
        const val REQUEST_REMOVE_USER = "request_remove_user"

        const val KEY_OPERATION_RESULT = "operation_result"
        const val KEY_NEW_USER = "new_user"
        const val KEY_USER_TO_UPDATE = "user_to_update"
        const val KEY_UPDATED_USER = "updated_user"
        const val KEY_USER_EMAIL_TO_REMOVE = "user_email_to_remove"

        const val OPERATION_ADD = "add"
        const val OPERATION_UPDATE = "update"
    }
}