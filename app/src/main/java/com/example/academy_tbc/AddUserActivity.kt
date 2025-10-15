package com.example.academy_tbc

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.academy_tbc.MainActivity.Companion.ADDRESS
import com.example.academy_tbc.MainActivity.Companion.BIRTHDAY
import com.example.academy_tbc.MainActivity.Companion.DESC
import com.example.academy_tbc.MainActivity.Companion.EMAIL
import com.example.academy_tbc.MainActivity.Companion.FIRST_NAME
import com.example.academy_tbc.MainActivity.Companion.ID
import com.example.academy_tbc.MainActivity.Companion.LAST_NAME
import com.example.academy_tbc.databinding.ActivityAddUserBinding
import com.example.academy_tbc.utils.Convertors.convertLongToDate
import com.example.academy_tbc.utils.Validations.isValidInput
import com.example.academy_tbc.utils.closeKeyboard
import com.example.academy_tbc.utils.showSnackBar
import com.example.academy_tbc.utils.toText

class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addUser()
    }

    private fun addUser() = with(binding) {
        btnSaveUser.setOnClickListener {
            if (!isValidInput(
                    context = this@AddUserActivity,
                    etId = etId,
                    etFirstName = etFirstName,
                    etLastName = etLastName,
                    etBirthday = etBirthday,
                    etAddress = etAddress,
                    etEmail = etEmail,
                    id = etId.toText(),
                    firstName = etFirstName.toText(),
                    lastName = etLastName.toText(),
                    birthday = etBirthday.toText(),
                    address = etAddress.toText(),
                    email = etEmail.toText()
                )
            ) {
                return@setOnClickListener
            }

            val id = etId.toText().toInt()
            val firstName = etFirstName.toText()
            val lastName = etLastName.toText()
            val formattedDate = etBirthday.toText()
            val address = etAddress.toText()
            val email = etEmail.toText()
            val userDesc = intent.getStringExtra(DESC)

            val user = UsersList.users.find { it.id == id }

            if (user != null) {
                root.showSnackBar(
                    title = getString(R.string.user_already_exists)
                )
                closeKeyboard(this@AddUserActivity)
                return@setOnClickListener
            }

            UsersList.users.add(
                User(
                    id = id,
                    firstName = firstName,
                    lastName = lastName,
                    birthday = formattedDate,
                    address = address,
                    email = email,
                    desc = userDesc
                )
            )

            val intent = Intent(this@AddUserActivity, MainActivity::class.java)
            intent.apply {
                putExtra(ID, id)
                putExtra(FIRST_NAME, firstName)
                putExtra(LAST_NAME, lastName)
                putExtra(BIRTHDAY, convertLongToDate(formattedDate))
                putExtra(ADDRESS, address)
                putExtra(EMAIL, email)
                putExtra(DESC, userDesc)
            }

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}