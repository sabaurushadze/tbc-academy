package com.example.academy_tbc

import android.content.Intent
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.academy_tbc.databinding.ActivityUserManagementBinding

class UserManagementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserManagementBinding
    private var users: HashMap<String, User> = hashMapOf()
    private var deletedUsers: Int = 0

    val userDetailsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                handleUserDetailsResult(data)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserManagementBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        crud()
    }

    private fun crud() {
        addUser()
        removeUser()
        updateUser()
        showStats()
    }

    private fun handleUserDetailsResult(data: Intent) {
        val firstName = data.getStringExtra(FIRST_NAME)
        val lastName = data.getStringExtra(LAST_NAME)
        val age = data.getStringExtra(AGE)
        val email = data.getStringExtra(EMAIL) ?: return

        val operation = data.getStringExtra(OPERATION)

        when (operation) {
            OPERATION_UPDATE -> {
                val currentUser = users[email]
                if (currentUser != null) {
                    val updatedUser = currentUser.copy(
                        firstName = firstName ?: currentUser.firstName,
                        lastName = lastName ?: currentUser.lastName,
                        age = age ?: currentUser.age
                    )
                    users[email] = updatedUser
                    binding.root.showSnackBar(getString(R.string.user_updated_successfully))
                }
            }

            OPERATION_ADD -> {
                if (firstName != null && lastName != null && age != null) {
                    val newUser = User(firstName, lastName, age)
                    users[email] = newUser
                    binding.root.showSnackBar(getString(R.string.user_added_successfully))
                }
            }
        }

        setTextToSuccessOrFailure(
            binding.tvOperationSuccessOrError, R.string.success, R.color.success_green
        )
        clearEmailField()
        showStats()
    }

    private fun addUser() = with(binding) {
        btnAddUser.setOnClickListener {
            val email = etEmail.toText()

            if (!isValidEmail(email)) {
                return@setOnClickListener
            }

            if (users.contains(email)) {
                root.showSnackBar(getString(R.string.error_user_already_exists))
                setTextToSuccessOrFailure(
                    tvOperationSuccessOrError, R.string.failure, R.color.error_red
                )
                clearEmailField()
                return@setOnClickListener
            }

            val intent = Intent(this@UserManagementActivity, UserDetailsActivity::class.java)
            intent.putExtra(EMAIL, email)
            intent.putExtra(OPERATION, OPERATION_ADD)
            userDetailsLauncher.launch(intent)
        }
    }

    private fun updateUser() = with(binding) {
        btnUpdateUser.setOnClickListener {
            val email = etEmail.toText()

            if (!isValidEmail(email)) {
                return@setOnClickListener
            }

            if (!users.contains(email)) {
                root.showSnackBar(getString(R.string.error_user_does_not_exist))
                setTextToSuccessOrFailure(
                    tvOperationSuccessOrError, R.string.failure, R.color.error_red
                )
                clearEmailField()
                return@setOnClickListener
            }

            val intent = Intent(this@UserManagementActivity, UserDetailsActivity::class.java)
            intent.putExtra(EMAIL, email)
            intent.putExtra(OPERATION, OPERATION_UPDATE)
            intent.putExtra(FIRST_NAME, users[email]?.firstName)
            intent.putExtra(LAST_NAME, users[email]?.lastName)
            intent.putExtra(AGE, users[email]?.age)
            userDetailsLauncher.launch(intent)
        }
    }

    private fun removeUser() = with(binding) {
        btnRemoveUser.setOnClickListener {
            val email = etEmail.toText()

            if (!isValidEmail(email)) {
                return@setOnClickListener
            }

            if (!users.contains(email)) {
                root.showSnackBar(getString(R.string.error_user_does_not_exist))
                setTextToSuccessOrFailure(
                    tvOperationSuccessOrError, R.string.failure, R.color.error_red
                )
                clearEmailField()
                return@setOnClickListener
            }

            users.remove(email)
            deletedUsers++
            showStats()
            root.showSnackBar(getString(R.string.user_deleted_successfully))
            setTextToSuccessOrFailure(
                tvOperationSuccessOrError, R.string.success, R.color.success_green
            )
            clearEmailField()
        }
    }


    private fun isValidEmail(email: String): Boolean = with(binding) {
        fun setError(view: EditText, message: String): Boolean {
            view.error = message
            return false
        }
        return when {
            email.isBlank() -> setError(
                etEmail, getString(R.string.error_please_enter_your_email)
            )

            !EMAIL_ADDRESS.matcher(email).matches() -> setError(
                etEmail, getString(R.string.error_invalid_email_format)
            )

            else -> true
        }
    }

    private fun showStats() = with(binding) {
        tvActiveUserCount.text = getString(R.string.active_users, users.size)
        tvDeletedUserCount.text = getString(R.string.deleted_users, deletedUsers)
    }

    private fun setTextToSuccessOrFailure(
        tvSuccessOrError: TextView,
        textId: Int,
        colorId: Int,
    ) {
        tvSuccessOrError.text = getString(textId)
        tvSuccessOrError.setTextColor(getColor(colorId))
    }

    private fun clearEmailField() = with(binding) {
        etEmail.text?.clear()
    }

    companion object {
        const val OPERATION_ADD = "add"
        const val OPERATION_UPDATE = "update"
        const val OPERATION = "operation"
        const val FIRST_NAME = "firstName"
        const val LAST_NAME = "lastName"
        const val AGE = "age"
        const val EMAIL = "email"
    }
}