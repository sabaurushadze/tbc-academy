package com.example.academy_tbc

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.academy_tbc.databinding.ActivityLoginBinding
import com.example.academy_tbc.utils.AuthError
import com.example.academy_tbc.utils.Validations.validateEmailAndPassword
import com.example.academy_tbc.utils.closeKeyboard
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeners()
    }

    private fun listeners() {
        setupBackButtonListener()
        onLogInButtonPressed()
    }

    private fun onLogInButtonPressed() {
        binding.btnLogIn.setOnClickListener {
            val email = binding.etEmailLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()

            if (validateEmailAndPassword(
                    context = this,
                    email = email,
                    password = password,
                    emailEditText = binding.etEmailLogin,
                    passwordEditText = binding.etPasswordLogin,
                )
            ) {
                logInWithEmailAndPassword(email, password)
            } else {
                return@setOnClickListener
            }
        }
    }

    private fun logInWithEmailAndPassword(email: String, password: String) {
        showLoading(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                showLoading(false)
                if (task.isSuccessful) {
                    startActivity(Intent(this, FeedActivity::class.java))
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