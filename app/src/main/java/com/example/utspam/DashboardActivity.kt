// DashboardActivity.kt
package com.example.utspam

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class DashboardActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var userList: MutableList<String> // Hanya menyimpan daftar email pengguna

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(mutableListOf(), this)
        recyclerView.adapter = userAdapter

        // Fetch user data from Firebase Auth
        fetchUserDataFromAuth()
    }

    private fun fetchUserDataFromAuth() {
        // Get the current user
        val currentUser: FirebaseUser? = auth.currentUser

        // Check if the user is signed in
        if (currentUser != null) {
            // Get the email of the current user
            val userEmail: String? = currentUser.email
            if (userEmail != null) {
                // Initialize user list and add the email to the list
                userList = mutableListOf()
                userList.add(userEmail)
                // Set user list to adapter
                userAdapter.setUserList(userList)
            }
        }
    }

    override fun onItemClick(position: Int) {
        // Handle item click, for example, open profile activity
        // Since we only have email information, we can't navigate to a profile activity directly
        // You can choose to perform any other action here based on the selected user
    }
}
