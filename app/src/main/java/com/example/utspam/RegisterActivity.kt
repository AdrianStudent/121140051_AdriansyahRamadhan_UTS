package com.example.utspam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {

    private lateinit var Username: TextInputEditText
    private lateinit var GitHubUsername: TextInputEditText
    private lateinit var NIK: TextInputEditText
    private lateinit var Email: TextInputEditText
    private lateinit var Password: TextInputEditText
    private lateinit var buttonRegister: Button
    private lateinit var textViewLogin: TextView

    private lateinit var auth: FirebaseAuth // Firebase Authentication instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance() // Initialize Firebase Authentication

        // Initialize views
        Username = findViewById(R.id.Username)
        GitHubUsername = findViewById(R.id.GitHubUsername)
        NIK = findViewById(R.id.NIK)
        Email = findViewById(R.id.Email)
        Password = findViewById(R.id.Password)
        buttonRegister = findViewById(R.id.buttonRegister)
        textViewLogin = findViewById(R.id.LoginNow)

        // Set click listener for the register button
        buttonRegister.setOnClickListener {
            // Retrieve input values
            val email = Email.text.toString().trim()
            val password = Password.text.toString().trim()

            // Validate input fields
            if (validateInputs(email, password)) {
                // Perform registration with Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            // You can save additional user information to Firebase Firestore here
                            updateUI(user)

                            // Redirect to LoginActivity
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish() // Finish this activity so user cannot go back to registration page by pressing back button
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
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

        // Set click listener for the Login TextView
        textViewLogin.setOnClickListener {
            // Handle login text view click
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        // Validate email format and password length
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val isEmailValid = email.isNotEmpty() && email.matches(emailPattern.toRegex())
        val isPasswordValid = password.isNotEmpty() && password.length >= 6

        // Return true if both email and password are valid
        return isEmailValid && isPasswordValid
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // User is signed in, update UI accordingly
            // For example, you can redirect the user to another activity upon successful registration
            // Here, let's just display a toast message indicating successful registration
            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
        } else {
            // Registration failed, update UI accordingly
            // For example, you can display a message indicating registration failure
            // Here, let's just display a toast message indicating registration failure
            Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}