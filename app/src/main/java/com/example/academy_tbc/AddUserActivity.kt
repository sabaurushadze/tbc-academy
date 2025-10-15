package com.example.academy_tbc

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.academy_tbc.MainActivity.Companion.ADDRESS
import com.example.academy_tbc.MainActivity.Companion.BIRTHDAY
import com.example.academy_tbc.MainActivity.Companion.EMAIL
import com.example.academy_tbc.MainActivity.Companion.FIRST_NAME
import com.example.academy_tbc.MainActivity.Companion.ID
import com.example.academy_tbc.MainActivity.Companion.LAST_NAME
import com.example.academy_tbc.databinding.ActivityAddUserBinding
import com.example.academy_tbc.utils.convertTimestampToDate
import com.example.academy_tbc.utils.toText
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

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
        setupBirthdayPicker()
        addUser()
    }

    private fun setupBirthdayPicker() = with(binding) {
        etBirthday.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this@AddUserActivity, { _, selectedYear, selectedMonth, selectedDay ->
                    calendar.set(selectedYear, selectedMonth, selectedDay)
                    val timestampString = calendar.timeInMillis.toString()
                    etBirthday.setText(convertTimestampToDate(timestampString))
                    etBirthday.tag = timestampString
                }, year, month, day
            )
            datePicker.show()
        }
    }

    private fun addUser() = with(binding) {
        btnSaveUser.setOnClickListener {
            when {
                etId.toText().isEmpty() -> {
                    etId.error = getString(R.string.id_field_should_not_be_empty)
                    return@setOnClickListener
                }

                etFirstName.toText().isEmpty() -> {
                    etFirstName.error = getString(R.string.first_name_should_not_be_empty)
                    return@setOnClickListener
                }

                etLastName.toText().isEmpty() -> {
                    etLastName.error = getString(R.string.last_name_should_not_be_empty)
                    return@setOnClickListener
                }

                etBirthday.toText().isEmpty() -> {
                    etBirthday.error = getString(R.string.birthday_should_not_be_empty)
                    return@setOnClickListener
                }

                etAddress.toText().isEmpty() -> {
                    etAddress.error = getString(R.string.address_should_not_be_empty)
                    return@setOnClickListener
                }

                etEmail.toText().isEmpty() -> {
                    etEmail.error = getString(R.string.email_should_not_be_empty)
                    return@setOnClickListener
                }

                !Patterns.EMAIL_ADDRESS.matcher(etEmail.toText()).matches() -> {
                    etEmail.error = getString(R.string.email_not_valid)
                    return@setOnClickListener
                }
            }

            val id = etId.toText().toInt()
            val firstName = etFirstName.toText()
            val lastName = etLastName.toText()
            val address = etAddress.toText()
            val email = etEmail.toText()

            val existingUser = UsersList.users.find { it.id == id }
            if (existingUser != null) {
                Snackbar.make(
                    root,
                    getString(R.string.user_with_that_id_already_exists),
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val birthdayTimestamp = etBirthday.tag?.toString() ?: run {
                etBirthday.error = getString(R.string.birthday_should_not_be_empty)
                return@setOnClickListener
            }

            UsersList.users.add(
                User(
                    id = id,
                    firstName = firstName,
                    lastName = lastName,
                    birthday = birthdayTimestamp,
                    address = address,
                    email = email
                )
            )

            val intent = Intent(this@AddUserActivity, MainActivity::class.java).apply {
                putExtra(ID, id)
                putExtra(FIRST_NAME, firstName)
                putExtra(LAST_NAME, lastName)
                putExtra(BIRTHDAY, birthdayTimestamp)
                putExtra(ADDRESS, address)
                putExtra(EMAIL, email)
            }
            startActivity(intent)
        }
    }
}