package com.example.utspam

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var Username: TextInputEditText
    private lateinit var Password: TextInputEditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewRegister: TextView

    private lateinit var auth: FirebaseAuth // Firebase Authentication instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance() // Initialize Firebase Authentication

        // Initialize views
        Username = findViewById(R.id.Username)
        Password = findViewById(R.id.Password)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewRegister = findViewById(R.id.RegisterNow)

        // Set click listener for the login button
        buttonLogin.setOnClickListener {
            // Retrieve input values
            val username = Username.text.toString().trim()
            val password = Password.text.toString().trim()

            // Validate input fields
            if (validateInputs(username, password)) {
                // Perform login with Firebase Authentication
                auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            if (user != null) {
                                // User is registered, proceed with login
                                updateUI(user)
                                // Start MainActivity and pass username as extra
                                val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                                intent.putExtra("USERNAME", username)
                                startActivity(intent)
                                finish()
                            } else {
                                // User is not registered, inform the user
                                Toast.makeText(
                                    baseContext,
                                    "User is not registered.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                updateUI(null)
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext,
                                "Authentication failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUI(null)
                        }
                    }
            }
        }

        // Set click listener for the Register TextView
        textViewRegister.setOnClickListener {
            // Handle register text view click
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun validateInputs(username: String, password: String): Boolean {
        // Validate username and password
        return username.isNotEmpty() && password.isNotEmpty()
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // User is signed in, update UI accordingly
            // For example, you can redirect the user to another activity upon successful login
            // Here, let's just display a toast message indicating successful login
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
        } else {
            // Login failed, update UI accordingly
            // For example, you can display a message indicating login failure
            // Here, let's just display a toast message indicating login failure
            Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
        }
    }
}