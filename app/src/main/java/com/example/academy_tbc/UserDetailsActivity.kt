package com.example.academy_tbc

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.academy_tbc.databinding.ActivityUserDetailsBinding
import com.example.academy_tbc.UserManagementActivity.Companion.AGE
import com.example.academy_tbc.UserManagementActivity.Companion.EMAIL
import com.example.academy_tbc.UserManagementActivity.Companion.FIRST_NAME
import com.example.academy_tbc.UserManagementActivity.Companion.LAST_NAME
import com.example.academy_tbc.UserManagementActivity.Companion.OPERATION

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeFields()
        save()
    }

    private fun initializeFields() {
        binding.etFirstName.setText(intent.getStringExtra(FIRST_NAME) ?: "")
        binding.etLastName.setText(intent.getStringExtra(LAST_NAME) ?: "")
        binding.etAge.setText(intent.getStringExtra(AGE) ?: "")
    }

    private fun save() = with(binding) {
        btnSave.setOnClickListener {
            val firstName: String = etFirstName.toText()
            val lastName = etLastName.toText()
            val age = etAge.toText()

            if (!isValidInput(firstName, lastName, age)) {
                return@setOnClickListener
            }

            val resultIntent = Intent().apply {
                putExtra(FIRST_NAME, firstName)
                putExtra(LAST_NAME, lastName)
                putExtra(AGE, age)
                putExtra(OPERATION, intent.getStringExtra(OPERATION))
                putExtra(EMAIL, intent.getStringExtra(EMAIL))
            }

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun isValidInput(
        firstName: String,
        lastName: String,
        age: String,
    ): Boolean = with(binding) {
        fun setError(view: EditText, message: String): Boolean {
            view.error = message
            return false
        }
        return when {
            firstName.isBlank() -> setError(
                etFirstName, getString(R.string.error_please_enter_your_first_name)
            )

            lastName.isBlank() -> setError(
                etLastName, getString(R.string.error_please_enter_your_last_name)
            )

            age.isBlank() -> setError(etAge, getString(R.string.error_please_enter_your_age))
            firstName.length !in 2..30 -> setError(
                etFirstName, getString(R.string.error_first_name_length_is_not_valid)
            )

            lastName.length !in 2..30 -> setError(
                etLastName, getString(R.string.error_last_name_length_is_not_valid)
            )

            age.toInt() !in 1..120 -> setError(
                etAge, getString(R.string.error_please_enter_your_real_age)
            )

            else -> true
        }
    }
}