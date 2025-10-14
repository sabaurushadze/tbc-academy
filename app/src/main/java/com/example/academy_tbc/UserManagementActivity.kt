package com.example.academy_tbc

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.academy_tbc.databinding.ActivityUserManagementBinding
import com.example.academy_tbc.utils.showSnackBar
import kotlin.random.Random

class UserManagementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserManagementBinding
    private var users: HashMap<String, User> = linkedMapOf()
    private var deletedUsers: Int = 0

    val userDetailsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            RESULT_ADD -> {
                result.data?.let { data ->
                    handleAddUser(data)
                    showStats()
                }
            }

            RESULT_UPDATE -> {
                result.data?.let { data ->
                    handleUpdateUser(data)
                    showStats()
                }
            }

            RESULT_DELETE -> {
                result.data?.let { data ->
                    handleRemoveUser(data)
                    showStats()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserManagementBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        applySystemWindowInsetsAsPadding(view)

        setUp()
    }

    private fun setUp() {
        addUser()
        updateUser()
        showStats()
    }

    private fun handleAddUser(data: Intent) = with(binding) {
        val user = data.getParcelableExtra<User>(USER) ?: return
        val email = data.getStringExtra(EMAIL) ?: return

        if (users.contains(email)) {
            root.showSnackBar(getString(R.string.user_already_exists))
            setTextToSuccessOrFailure(
                tvOperationSuccessOrError, R.string.failure, R.color.error_red
            )
        } else {
            users[email] = user
            root.showSnackBar(getString(R.string.user_added_successfully))
            setTextToSuccessOrFailure(
                tvOperationSuccessOrError, R.string.success, R.color.success_green
            )
        }
    }

    private fun handleUpdateUser(data: Intent) = with(binding) {
        val user = data.getParcelableExtra<User>(USER) ?: return
        val email = data.getStringExtra(EMAIL) ?: return

        users[email] = user
        binding.root.showSnackBar(getString(R.string.user_updated_successfully))
        setTextToSuccessOrFailure(
            binding.tvOperationSuccessOrError, R.string.success, R.color.success_green
        )
    }

    private fun handleRemoveUser(data: Intent) = with(binding) {
        val email = data.getStringExtra(EMAIL) ?: return

        users.remove(email)
        deletedUsers++
        root.showSnackBar(getString(R.string.user_deleted_successfully))
        setTextToSuccessOrFailure(
            tvOperationSuccessOrError, R.string.success, R.color.success_green
        )
    }

    private fun addUser() = with(binding) {
        btnAddUser.setOnClickListener {
            launchUserDetailsActivity(operation = OPERATION_ADD)
        }
    }

    private fun updateUser() = with(binding) {
        btnUpdateUser.setOnClickListener {
            if (users.isEmpty()) {
                binding.root.showSnackBar(getString(R.string.error_no_users_found))
                setTextToSuccessOrFailure(
                    binding.tvOperationSuccessOrError, R.string.failure, R.color.error_red
                )
                return@setOnClickListener
            } else {
                val randomUser = Random.nextInt(0, users.size)
                val randomUserEmail = users.keys.toList()[randomUser]

                launchUserDetailsActivity(
                    operation = OPERATION_UPDATE,
                    email = randomUserEmail,
                    user = users[randomUserEmail]
                )
            }
        }
    }

    private fun launchUserDetailsActivity(
        email: String? = null,
        user: User? = null,
        operation: String,
    ) {
        val intent = Intent(this@UserManagementActivity, UserDetailsActivity::class.java)
        intent.apply {
            email?.let { putExtra(EMAIL, it) }
            user?.let { putExtra(USER, it) }
            putExtra(OPERATION, operation)
        }
        userDetailsLauncher.launch(intent)
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

    private fun applySystemWindowInsetsAsPadding(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        const val RESULT_ADD = RESULT_OK
        const val RESULT_UPDATE = RESULT_FIRST_USER
        const val RESULT_DELETE = RESULT_FIRST_USER + 1
        const val USER = "user"
        const val OPERATION_ADD = "add"
        const val OPERATION_UPDATE = "update"
        const val OPERATION_DELETE = "delete"
        const val OPERATION = "operation"
        const val EMAIL = "email"
    }
}