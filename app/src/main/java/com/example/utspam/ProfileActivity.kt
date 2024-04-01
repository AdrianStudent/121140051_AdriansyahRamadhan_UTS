package com.example.utspam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val usernameTextView = findViewById<TextView>(R.id.usernameTextView)
        val emailTextView = findViewById<TextView>(R.id.emailTextView)

        val username = intent.getStringExtra("USERNAME")
        val email = intent.getStringExtra("EMAIL")

        usernameTextView.text = "Username: $username"
        emailTextView.text = "Email: $email"
    }
}
