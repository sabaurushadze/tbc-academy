package com.example.academy_tbc

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.academy_tbc.UserManagementActivity.Companion.EMAIL
import com.example.academy_tbc.UserManagementActivity.Companion.OPERATION
import com.example.academy_tbc.UserManagementActivity.Companion.OPERATION_ADD
import com.example.academy_tbc.UserManagementActivity.Companion.OPERATION_DELETE
import com.example.academy_tbc.UserManagementActivity.Companion.OPERATION_UPDATE
import com.example.academy_tbc.UserManagementActivity.Companion.RESULT_ADD
import com.example.academy_tbc.UserManagementActivity.Companion.RESULT_DELETE
import com.example.academy_tbc.UserManagementActivity.Companion.RESULT_UPDATE
import com.example.academy_tbc.UserManagementActivity.Companion.USER
import com.example.academy_tbc.databinding.ActivityUserDetailsBinding
import com.example.academy_tbc.utils.Validations.isValidEmail
import com.example.academy_tbc.utils.Validations.isValidInput
import com.example.academy_tbc.utils.toText

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        applySystemWindowInsetsAsPadding(view)

        showButtonsBasedOnOperation()
        initializeUpdateScreenFields()
        addUser()
        updateUser()
        removeUser()
    }

    private fun createUserIntent(resultCode: Int, user: User? = null, email: String? = null) {
        val intent = Intent().apply {
            user?.let { putExtra(USER, it) }
            email?.let { putExtra(EMAIL, it) }
        }

        setResult(resultCode, intent)
        finish()
    }

    private fun initializeUpdateScreenFields() = with(binding) {
        when (intent.getStringExtra(OPERATION)) {
            OPERATION_UPDATE -> {
                etEmail.isEnabled = false

                val user = intent.getParcelableExtra<User>(USER)
                val email = intent.getStringExtra(EMAIL)

                etFirstName.setText(user?.firstName)
                etLastName.setText(user?.lastName)
                etAge.setText(user?.age)
                etEmail.setText(email)
            }
        }
    }

    private fun showButtonsBasedOnOperation() = with(binding) {
        when (intent.getStringExtra(OPERATION)) {
            OPERATION_UPDATE, OPERATION_DELETE -> {
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

    private fun addUser() = with(binding) {
        btnAddUser.setOnClickListener {
            val firstName: String = etFirstName.toText()
            val lastName = etLastName.toText()
            val age = etAge.toText()
            val email = etEmail.toText().lowercase()

            if (!(isValidEmail(
                    context = this@UserDetailsActivity, etEmail = etEmail, email = email
                ) && isValidInput(
                    context = this@UserDetailsActivity,
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
            createUserIntent(resultCode = RESULT_ADD, user = user, email = email)
        }
    }

    private fun updateUser() = with(binding) {
        btnUpdateUser.setOnClickListener {
            val firstName: String = etFirstName.toText()
            val lastName = etLastName.toText()
            val age = etAge.toText()
            val email = etEmail.toText()

            if (!isValidInput(
                    context = this@UserDetailsActivity,
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
            val originalUser = intent.getParcelableExtra<User>(USER)
            val updatedUser = originalUser?.copy(firstName, lastName, age)
            createUserIntent(resultCode = RESULT_UPDATE, user = updatedUser, email = email)
        }
    }

    private fun removeUser() = with(binding) {
        btnRemoveUser.setOnClickListener {
            val email = intent.getStringExtra(EMAIL)
            createUserIntent(resultCode = RESULT_DELETE, email = email)
        }
    }

    private fun applySystemWindowInsetsAsPadding(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}