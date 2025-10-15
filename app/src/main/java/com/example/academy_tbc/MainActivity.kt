package com.example.academy_tbc

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.academy_tbc.databinding.ActivityMainBinding
import com.example.academy_tbc.utils.Convertors.convertLongToDate
import com.example.academy_tbc.utils.closeKeyboard
import com.example.academy_tbc.utils.showSnackBar
import com.example.academy_tbc.utils.toText

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val addUserLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            RESULT_OK -> {
                result.data?.let { data ->
                    handleAddUser(data)
                }
            }
        }
    }

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
        searchAndDisplayUserInfo()
        goToAddUserActivity()
    }

    private fun handleAddUser(data: Intent) = with(binding) {
        tvId.text = getString(R.string.display_id, data.getIntExtra(ID, -1).toString())
        tvFirstName.text = getString(R.string.display_first_name, data.getStringExtra(FIRST_NAME))
        tvLastName.text = getString(R.string.display_last_name, data.getStringExtra(LAST_NAME))
        tvBirthday.text = getString(R.string.display_birthday, data.getStringExtra(BIRTHDAY))
        tvAddress.text = getString(R.string.display_address, data.getStringExtra(ADDRESS))
        tvEmail.text = getString(R.string.display_email, data.getStringExtra(EMAIL))
        tvDesc.text = getString(R.string.display_desc, data.getStringExtra(DESC))
    }

    private fun searchAndDisplayUserInfo() = with(binding) {
        val users = UsersList.users
        btnSearch.setOnClickListener {
            if (etUserInfoField.toText().isEmpty()) {
                etUserInfoField.error = getString(R.string.search_field_should_not_be_empty)
                return@setOnClickListener
            }
            val userInfoSearchField = etUserInfoField.toText().lowercase()
            val userBirthday = etUserInfoField.toText()

            val existingUser = users.find {
                val birthdayText = convertLongToDate(it.birthday)
                val fullName = "${it.firstName} ${it.lastName}".lowercase()
                val fullNameReversed = "${it.lastName} ${it.firstName}".lowercase()

                userInfoSearchField == fullName ||
                        userInfoSearchField == fullNameReversed ||
                        userBirthday == birthdayText ||
                        userInfoSearchField == it.id.toString() ||
                        userInfoSearchField == it.firstName.lowercase() ||
                        userInfoSearchField == it.lastName.lowercase() ||
                        userInfoSearchField == it.address.lowercase() ||
                        userInfoSearchField == it.email.lowercase() ||
                        userInfoSearchField == it.desc?.lowercase()
            }

            if (existingUser == null) {
                root.showSnackBar(
                    title = getString(R.string.user_not_found),
                )
                closeKeyboard(this@MainActivity)
                btnGoToAddNewUser.visibility = View.VISIBLE
                clearFields()
                return@setOnClickListener
            }

            tvId.text = getString(R.string.id, existingUser.id)
            tvFirstName.text = getString(R.string.first_name, existingUser.firstName)
            tvLastName.text = getString(R.string.last_name, existingUser.lastName)
            tvBirthday.text = getString(R.string.birthday, convertLongToDate(existingUser.birthday))
            tvAddress.text = getString(R.string.address, existingUser.address)
            tvEmail.text = getString(R.string.email, existingUser.email)
            tvDesc.text = getString(R.string.desc, existingUser.desc)
        }
    }

    private fun goToAddUserActivity() = with(binding) {
        btnGoToAddNewUser.setOnClickListener {
            val intent = Intent(this@MainActivity, AddUserActivity::class.java)
            intent.putExtra(DESC, etUserInfoField.toText())
            addUserLauncher.launch(intent)
            btnGoToAddNewUser.visibility = View.GONE
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
    }

    companion object {
        const val ID = "id"
        const val FIRST_NAME = "firstName"
        const val LAST_NAME = "lastName"
        const val BIRTHDAY = "birthday"
        const val ADDRESS = "address"
        const val EMAIL = "email"
        const val DESC = "desc"
    }
}