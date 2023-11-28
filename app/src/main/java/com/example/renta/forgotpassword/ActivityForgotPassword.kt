package com.example.renta.forgotpassword

import androidx.annotation.NonNull;
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.renta.R
import com.google.firebase.auth.FirebaseAuth


class ActivityForgotPassword: AppCompatActivity() {

    // Declaration
    private lateinit var btnReset: Button

    private lateinit var edtEmail: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var mAuth: FirebaseAuth
    private lateinit var strEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Initialization

        btnReset = findViewById(R.id.forget_password_next_btn)
        edtEmail = findViewById(R.id.forget_password_phone_number)
        progressBar = findViewById(R.id.forgetPasswordProgressbar)

        mAuth = FirebaseAuth.getInstance()


        // Reset Button Listener
        btnReset.setOnClickListener {
            strEmail = edtEmail.text.toString().trim()
            if (!TextUtils.isEmpty(strEmail)) {
                resetPassword()
            } else {
                edtEmail.error = "Email field can't be empty"
            }
        }


        val backButton = findViewById<ImageButton>(R.id.forget_password_back_btn)
        backButton.setOnClickListener {
            // Handle back button click
            onBackPressed()
        }

        }
    private fun resetPassword() {
        progressBar.visibility = View.VISIBLE
        btnReset.visibility = View.INVISIBLE

        mAuth.sendPasswordResetEmail(strEmail)
            .addOnSuccessListener {
                // Password reset email sent successfully
                Toast.makeText(this@ActivityForgotPassword, "Reset Password link has been sent to your registered Email", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ActivityForgotPassword, com.example.renta.forgotpassword.ActivitySuccessPasswordChanged::class.java)
                startActivity(intent)
                finish()
                // Additional UI changes (if needed)
                progressBar.visibility = View.INVISIBLE
                btnReset.visibility = View.VISIBLE
            }
            .addOnFailureListener { e ->
                // Error in sending password reset email
                Toast.makeText(this@ActivityForgotPassword, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                // Additional UI changes (if needed)
                progressBar.visibility = View.INVISIBLE
                btnReset.visibility = View.VISIBLE

                // Log the error for debugging
                Log.e("PasswordReset", "Error sending password reset email: ${e.message}", e)
            }
    }

}

