package com.example.academy_tbc

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.academy_tbc.databinding.ActivityFeedBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class FeedActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFeedBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnLogOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }

        val user = Firebase.auth.currentUser
        binding.tvEmail.text = user?.email
    }


}