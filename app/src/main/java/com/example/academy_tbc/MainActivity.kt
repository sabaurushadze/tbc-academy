package com.example.academy_tbc

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.academy_tbc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val initProfileInfo: LinearLayoutCompat = binding.initProfileInfo
        val etEmail = binding.etEmail
        val etUserName = binding.etUsername
        val etFirstName = binding.etFirstName
        val etLastName = binding.etLastName
        val etAge = binding.etAge
        val btnSave = binding.btnSave
        val btnClear = binding.btnClear

        val displayProfileInfo: LinearLayoutCompat = binding.displayProfileInfo
        val tvEmail = binding.tvEmail
        val tvUserName = binding.tvUsername
        val tvFirstNameAndLastName = binding.tvFirstNameAndLastName
        val tvAge = binding.tvAge
        val btnAgain = binding.btnAgain


        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        btnSave.setOnClickListener {
            if (etEmail.text!!.isEmpty()) {
                etEmail.error = getString(R.string.email_cant_be_empty)
                return@setOnClickListener
            } else if (!isValidEmail(etEmail.text.toString())) {
                etEmail.error = getString(R.string.wrong_email_format)
                return@setOnClickListener
            } else if (etUserName.text!!.isEmpty()) {
                etUserName.error = getString(R.string.user_name_cant_be_empty)
                return@setOnClickListener
            } else if (etUserName.text.toString().length < 10 || etUserName.text!!.length >= 30) {
                etUserName.error = getString(R.string.username_length_not_valid)
                return@setOnClickListener
            } else if (etFirstName.text!!.isEmpty()) {
                etFirstName.error = getString(R.string.first_name_cant_be_empty)
                return@setOnClickListener
            } else if (etFirstName.text!!.length >= 25) {
                etFirstName.error = getString(R.string.first_name_too_long)
                return@setOnClickListener
            } else if (etLastName.text!!.isEmpty()) {
                etLastName.error = getString(R.string.last_name_cant_be_empty)
                return@setOnClickListener
            } else if (etLastName.text!!.length >= 25) {
                etLastName.error = getString(R.string.last_name_too_long)
                return@setOnClickListener
            } else if (etAge.text!!.isEmpty()) {
                etAge.error = getString(R.string.age_cant_be_empty)
                return@setOnClickListener
            } else if (etAge.text!!.toString().toInt() == 0 || etAge.text!!.toString()
                    .toInt() > 120
            ) {
                etAge.error = getString(R.string.age_not_valid)
                return@setOnClickListener
            }

            tvEmail.text = "Email: ${etEmail.text}"
            tvUserName.text = "Username: ${etUserName.text}"
            tvFirstNameAndLastName.text = "Full Name: ${etFirstName.text} ${etLastName.text}"
            tvAge.text = "Age: ${etAge.text}"
            initProfileInfo.visibility = View.GONE
            displayProfileInfo.visibility = View.VISIBLE
        }

        btnClear.setOnLongClickListener {
            clearEditTextFields(etEmail, etUserName, etFirstName, etLastName, etAge)
            return@setOnLongClickListener true
        }

        btnAgain.setOnClickListener {
            clearEditTextFields(etEmail, etUserName, etFirstName, etLastName, etAge)
            displayProfileInfo.visibility = View.GONE
            initProfileInfo.visibility = View.VISIBLE
        }
    }

    private fun clearEditTextFields(vararg fields: AppCompatEditText) {
        fields.forEach {
            it.text?.clear()
        }
    }
}