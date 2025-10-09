package com.example.academy_tbc

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.academy_tbc.databinding.ActivityRegisterUsernameBinding
import com.example.academy_tbc.utils.Validations.validateUsername

class RegisterUsernameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterUsernameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterUsernameBinding.inflate(layoutInflater)
        setupBackButtonListener()
        setContentView(binding.root)

        listeners()
    }


    private fun listeners() {
        setupBackButtonListener()
        onSignUpButtonPressed()
    }
    private fun onSignUpButtonPressed() {
        binding.btnSignUp.setOnClickListener {
            val username = binding.etUsername.text.toString()

            if (validateUsername(
                    context = this,
                    username = username,
                    usernameEditText = binding.etUsername
                )
            ) {
                startActivity(Intent(this, FeedActivity::class.java))
            }
        }
    }

    private fun setupBackButtonListener() {
        binding.ibBackArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}