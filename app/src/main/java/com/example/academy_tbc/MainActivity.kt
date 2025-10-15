package com.example.academy_tbc

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.academy_tbc.databinding.ActivityMainBinding
import com.example.academy_tbc.utils.convertTimestampToDate
import com.example.academy_tbc.utils.toText
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listeners()
    }

    private fun listeners() {
        displayUserInfo()
        goToAddUserActivity()
        newUser()
        addDescToUser()
    }


    private fun displayUserInfo() = with(binding) {
        val users = UsersList.users
        btnSearch.setOnClickListener {
            if (etUserInfoField.toText().isEmpty()) {
                etUserInfoField.error = getString(R.string.search_field_should_not_be_empty)
                clearFields()
                return@setOnClickListener
            }
            val userInfoSearchField = etUserInfoField.toText().lowercase()
            val userBirthday = etUserInfoField.toText()
            val foundUser = users.find {
                val fullName = "${it.firstName} ${it.lastName}".lowercase()
                val birthdayText = convertTimestampToDate(it.birthday) // no lowercase
                fullName == userInfoSearchField ||
                        birthdayText == userBirthday ||
                        it.email.lowercase() == userInfoSearchField ||
                        it.desc?.lowercase() == userInfoSearchField ||
                        it.address.lowercase() == userInfoSearchField
            }

            if (foundUser == null) {
                Snackbar.make(
                    root, getString(R.string.user_not_found), Snackbar.LENGTH_SHORT
                ).show()
                btnGoToAddNewUser.visibility = View.VISIBLE
                clearFields()
                return@setOnClickListener
            }

            tvId.text = "id :" + foundUser?.id.toString()
            tvFirstName.text = "First Name: " + foundUser?.firstName.toString()
            tvLastName.text = "Last Name: " + foundUser?.lastName.toString()
            tvBirthday.text = "Birthday: " + convertTimestampToDate(foundUser?.birthday.toString())
            tvAddress.text = "Address: " + foundUser?.address.toString()
            tvEmail.text = "Email: " + foundUser?.email.toString()
            tvDesc.text = "desc: " + foundUser?.desc.toString()

        }
    }

    private fun addDescToUser() = with(binding) {
        btnAddDescription.setOnClickListener {
            if (etIdToUpdate.toText().isEmpty()) {
                etIdToUpdate.error = getString(R.string.id_should_not_be_empty)
                return@setOnClickListener
            }
            if (etIdToUpdate.toText().isEmpty()) {
                etDescToUpdate.error = getString(R.string.desc_should_not_be_empty)
            }
            val user = UsersList.users.find { it.id == etIdToUpdate.toText().toInt() }

            user?.apply { desc = etDescToUpdate.toText() }
        }

    }


    private fun goToAddUserActivity() {
        binding.btnGoToAddNewUser.setOnClickListener() {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clearFields() = with(binding) {
        tvId.text = ""
        tvFirstName.text = ""
        tvLastName.text = ""
        tvBirthday.text = ""
        tvAddress.text = ""
        tvEmail.text = ""
        tvDesc.text = ""

        etIdToUpdate.text?.clear()
        etDescToUpdate.text?.clear()
    }

    private fun newUser() = with(binding) {
        val id = intent.getIntExtra(ID, -1)
        val firstName = intent.getStringExtra(FIRST_NAME) ?: ""
        val lastName = intent.getStringExtra(LAST_NAME) ?: ""
        val birthday = intent.getStringExtra(BIRTHDAY) ?: ""
        val address = intent.getStringExtra(ADDRESS) ?: ""
        val email = intent.getStringExtra(EMAIL) ?: ""

        tvId.text = id.toString()
        tvFirstName.text = firstName
        tvLastName.text = lastName
        tvBirthday.text = if (birthday.isNotEmpty()) convertTimestampToDate(birthday) else ""
        tvAddress.text = address
        tvEmail.text = email

        if (id == -1) {
            tvId.text = ""
        }
    }

    companion object {
        val ID = "id"
        val FIRST_NAME = "firstName"
        val LAST_NAME = "lastName"
        val BIRTHDAY = "birthday"
        val ADDRESS = "address"
        val EMAIL = "email"
    }
}