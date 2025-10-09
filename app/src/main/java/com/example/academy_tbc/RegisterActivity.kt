package com.example.academy_tbc

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.academy_tbc.databinding.ActivityRegisterBinding
import com.example.academy_tbc.utils.AuthError
import com.example.academy_tbc.utils.Validations.validateEmailAndPassword
import com.example.academy_tbc.utils.closeKeyboard
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeners()
    }

    private fun listeners() {
        setupBackButtonListener()
        onRegisterNextButtonPressed()
    }
    private fun onRegisterNextButtonPressed() {
        binding.btnRegisterNext.setOnClickListener {
            val email = binding.etEmailRegister.text.toString()
            val password = binding.etPasswordRegister.text.toString()

            if (validateEmailAndPassword(
                    context = this,
                    email = email,
                    password = password,
                    emailEditText = binding.etEmailRegister,
                    passwordEditText = binding.etPasswordRegister,
                )
            ) {
                closeKeyboard(this)
                registerWithEmailAndPassword(email, password)
            } else {
                return@setOnClickListener
            }
        }
    }

    private fun registerWithEmailAndPassword(email: String, password: String) {
        showLoading(true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                showLoading(false)
                if (task.isSuccessful) {
                    startActivity(Intent(this, RegisterUsernameActivity::class.java))
                } else {
                    closeKeyboard(this)
                    val exception = task.exception

                    val errorNum = AuthError.fromException(exception)
                    val errorMessage = getString(errorNum.messageResId)

                    Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
                }
            }
    }

    private fun setupBackButtonListener() {
        binding.ibBackArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}