package com.example.renta

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    private lateinit var loginUsername: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var signupRedirectText: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var password_forgot: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginUsername = findViewById(R.id.login_username)
        loginPassword = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)
        signupRedirectText = findViewById(R.id.signupRedirectText)
        password_forgot = findViewById(R.id.forgot_password)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        firebaseAuth = FirebaseAuth.getInstance()

        // Check if the user is already logged in
        if (sharedPreferences.getBoolean("isUserLoggedIn", false)) {
            redirectToMainActivity()
        }

        // Redirecting to forgot activity if the user forget the credentials needed
        password_forgot.setOnClickListener {
            val intent = Intent(this@LoginActivity, com.example.renta.forgotpassword.ActivityForgotPassword::class.java)
            startActivity(intent)
        }


        loginButton.setOnClickListener {
            if (!validateUsername() or !validatePassword()) {
                // Handle validation failure
            } else {
                checkUser()
            }
        }

        signupRedirectText.setOnClickListener {
            val intent = Intent(this@LoginActivity, ActivitySignup::class.java)
            startActivity(intent)
        }
    }

    private fun validateUsername(): Boolean {
        val valText = loginUsername.text.toString()
        return if (valText.isEmpty()) {
            loginUsername.error = "Username cannot be empty"
            false
        } else {
            loginUsername.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        val valText = loginPassword.text.toString()
        return if (valText.isEmpty()) {
            loginPassword.error = "Password cannot be empty"
            false
        } else {
            loginPassword.error = null
            true
        }
    }

    private fun checkUser() {
        val inputText = loginUsername.text.toString().trim()
        val userPassword = loginPassword.text.toString().trim()

        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
        val checkUserDatabase: Query

        checkUserDatabase = if (android.util.Patterns.EMAIL_ADDRESS.matcher(inputText).matches()) {
            // Input is a valid email address
            reference.orderByChild("email").equalTo(inputText)
        } else {
            // Input is not a valid email address, treat it as a username
            reference.orderByChild("username").equalTo(inputText)
        }

        checkUserDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    loginUsername.error = null

                    val user = snapshot.children.first()

                    val passwordFromDB = user.child("password").getValue(String::class.java)

                    if (passwordFromDB == userPassword) {
                        loginUsername.error = null

                        // Save login status in SharedPreferences
                        saveLoginStatus(true)

                        val nameFromDB = user.child("name").getValue(String::class.java)
                        val emailFromDB = user.child("email").getValue(String::class.java)
                        val usernameFromDB = user.child("username").getValue(String::class.java)

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)

                        intent.putExtra("name", nameFromDB)
                        intent.putExtra("email", emailFromDB)
                        intent.putExtra("username", usernameFromDB)
                        intent.putExtra("password", passwordFromDB)

                        startActivity(intent)
                    } else {
                        loginPassword.error = "Invalid Credentials"
                        loginPassword.requestFocus()
                    }
                } else {
                    loginUsername.error = "User does not exist"
                    loginUsername.requestFocus()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the onCancelled event
            }
        })
    }

    private fun saveLoginStatus(status: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isUserLoggedIn", status)
        editor.apply()
    }

    private fun redirectToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish() // Close the LoginActivity to prevent going back
    }


}
