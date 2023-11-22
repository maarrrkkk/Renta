package com.example.renta

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import android.graphics.Color


class ActivitySignup : AppCompatActivity() {

    private lateinit var signupName: EditText
    private lateinit var signupUsername: EditText
    private lateinit var signupEmail: EditText
    private lateinit var signupPassword: EditText
    private lateinit var signupRetypePassword: EditText
    private lateinit var loginRedirectText: TextView
    private lateinit var signupButton: Button
    private lateinit var signupPhoneNumber: EditText
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference



    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

                signupName = findViewById(R.id.signup_name)
                signupEmail = findViewById(R.id.signup_email)
                signupUsername = findViewById(R.id.signup_username)
                signupPassword = findViewById(R.id.signup_password)
                signupRetypePassword = findViewById(R.id.signup_retype_password)
                loginRedirectText = findViewById(R.id.loginRedirectText)
                signupButton = findViewById(R.id.signup_button)
                signupPhoneNumber = findViewById(R.id.signup_phone_number)

                signupButton.setOnClickListener {


            val name = signupName.text.toString()
            val email = signupEmail.text.toString()
            val username = signupUsername.text.toString()
            val password = signupPassword.text.toString()
            val phoneNumber = signupPhoneNumber.text.toString()



                    // Checking if any of the fields is empty
                    if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
                        Toast.makeText(this@ActivitySignup, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    // Check if the email contains '@' sign
                    if (!email.contains('@')) {
                        Toast.makeText(this@ActivitySignup, "Invalid email address. Please include the '@' sign.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    val retypePassword = signupRetypePassword.text.toString()

                    if (password != retypePassword) {
                        // Passwords do not match, show a toast and return
                        Toast.makeText(this@ActivitySignup, "Passwords do not match!", Toast.LENGTH_SHORT).show()

                        signupPassword.setTextColor(Color.RED)
                        signupRetypePassword.setTextColor(Color.RED)

                        return@setOnClickListener
                    }

                    // If all checks pass, proceed with sign up
                    database = FirebaseDatabase.getInstance()
                    reference = database.getReference("users")

            val helperClass = HelperClass(name, email, username, password, phoneNumber)
            reference.child(username).setValue(helperClass)

            Toast.makeText(this@ActivitySignup, "You have signed up successfully!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@ActivitySignup, LoginActivity::class.java)
            startActivity(intent)
        }

                loginRedirectText.setOnClickListener {
            val intent = Intent(this@ActivitySignup, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}