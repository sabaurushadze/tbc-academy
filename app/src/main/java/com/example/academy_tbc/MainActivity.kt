package com.example.academy_tbc

import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.academy_tbc.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var users: MutableMap<String, String> = mutableMapOf()
    private var usersCount: Int = 0
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

        binding.tvUsersCount.text = getString(R.string.user_amount, usersCount)
        addUser()
        getUserInfo()
    }

    private fun addUser() {
        binding.btnAddUser.setOnClickListener {
            if (binding.etFullName.text!!.isEmpty()) {
                binding.etFullName.error = getString(R.string.full_name_cant_be_empty)
                return@setOnClickListener
            }
            if (binding.etFullName.text!!.length < 5 || binding.etFullName.text!!.length > 28) {
                binding.etFullName.error = getString(R.string.full_name_length_not_appropriate)
                return@setOnClickListener
            }
            if (binding.etEmail.text!!.isEmpty()) {
                binding.etEmail.error = getString(R.string.email_cant_be_empty)
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) {
                binding.etEmail.error = getString(R.string.email_is_not_valid)
                return@setOnClickListener
            }
            if (binding.etEmail.text!!.length < 6 || binding.etEmail.text!!.length > 30) {
                binding.etEmail.error = getString(R.string.email_length_not_appropriate)
                return@setOnClickListener
            }
            if (users.containsKey(binding.etEmail.text.toString())) {
                binding.etEmail.error = getString(R.string.email_is_already_present)
                return@setOnClickListener
            }

            users[binding.etEmail.text.toString()] = binding.etFullName.text.toString()
            binding.tvUserInfo.text = ""
            usersCount++
            binding.tvUsersCount.text = getString(R.string.user_amount, usersCount)
            binding.etFullName.text!!.clear()
            binding.etEmail.text!!.clear()

        }
    }

    private fun getUserInfo() {
        binding.btnGetUserInfo.setOnClickListener {
            if (binding.etFindUser.text!!.isEmpty()) {
                binding.etFindUser.error = getString(R.string.email_cant_be_empty)
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.etFindUser.text.toString().lowercase())
                    .matches()
            ) {
                binding.etFindUser.error = getString(R.string.email_is_not_valid)
                return@setOnClickListener
            }
            if (!users.containsKey(binding.etFindUser.text.toString().lowercase())) {
                binding.tvUserInfo.text = getString(R.string.user_not_found)
                return@setOnClickListener
            }
            binding.tvUserInfo.text = getString(
                R.string.user_info,
                users[binding.etFindUser.text.toString().lowercase()],
                binding.etFindUser.text.toString().lowercase()
            )
        }
    }
}