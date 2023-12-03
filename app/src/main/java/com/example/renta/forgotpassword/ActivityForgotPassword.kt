package com.example.renta.forgotpassword

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
import com.example.renta.LoginActivity
import com.example.renta.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class ActivityForgotPassword : AppCompatActivity() {

    // Declaration
    private lateinit var btnReset: Button
    private lateinit var edtPhoneNumber: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var mAuth: FirebaseAuth
    private lateinit var strPhoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Initialization
        btnReset = findViewById(R.id.forget_password_reset_btn)
        edtPhoneNumber = findViewById(R.id.forget_password_phone_number)
        progressBar = findViewById(R.id.forgetPasswordProgressbar)

        mAuth = FirebaseAuth.getInstance()

        // Reset Button Listener
        btnReset.setOnClickListener {
            strPhoneNumber = edtPhoneNumber.text.toString().trim()
            if (!TextUtils.isEmpty(strPhoneNumber)) {
                sendVerificationCode()
            } else {
                edtPhoneNumber.error = "Phone number field can't be empty"
            }
        }

        val backButton = findViewById<ImageButton>(R.id.forget_password_back_btn)
        backButton.setOnClickListener {
            // Handle back button click
            onBackPressed()
        }
    }

    private fun sendVerificationCode() {
        progressBar.visibility = View.VISIBLE
        btnReset.visibility = View.INVISIBLE

        // Use Firebase PhoneAuthProvider to send verification code
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            strPhoneNumber,
            60, // Timeout duration
            java.util.concurrent.TimeUnit.SECONDS,
            this,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Auto-retrieval or instant verification completed
                    // Proceed with verification
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // Handle verification failure
                    Toast.makeText(
                        this@ActivityForgotPassword,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar.visibility = View.INVISIBLE
                    btnReset.visibility = View.VISIBLE
                    Log.e("PhoneVerification", "Error sending verification code: ${e.message}", e)
                }
            }
        )
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                // Phone number verified successfully
                // Proceed with the password reset logic
                resetPassword()
            }
            .addOnFailureListener { e ->
                // Handle verification failure
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(
                        this@ActivityForgotPassword,
                        "Invalid verification code",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (e is FirebaseAuthInvalidUserException) {
                    Toast.makeText(
                        this@ActivityForgotPassword,
                        "Invalid phone number",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@ActivityForgotPassword,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                progressBar.visibility = View.INVISIBLE
                btnReset.visibility = View.VISIBLE
                Log.e("PhoneVerification", "Error verifying phone number: ${e.message}", e)
            }
    }



    private fun resetPassword() {
        // Send a password reset email to the user's email address
        mAuth.sendPasswordResetEmail(strPhoneNumber)
            .addOnSuccessListener {
                // Password reset email sent successfully
                Toast.makeText(
                    this@ActivityForgotPassword,
                    "Password reset email sent. Check your email.",
                    Toast.LENGTH_SHORT
                ).show()

                // Navigate to the login screen or any other desired screen
                val intent = Intent(this@ActivityForgotPassword, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                // Handle password reset email sending failure
                Toast.makeText(
                    this@ActivityForgotPassword,
                    "Error sending password reset email: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

                // Show the reset button and hide the progress bar
                progressBar.visibility = View.INVISIBLE
                btnReset.visibility = View.VISIBLE
                Log.e("ResetPassword", "Error sending password reset email: ${e.message}", e)
            }
    }

}
