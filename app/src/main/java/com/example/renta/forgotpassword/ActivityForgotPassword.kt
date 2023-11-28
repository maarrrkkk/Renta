package com.example.renta.forgotpassword


import android.media.Image
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.renta.R


class ActivityForgotPassword: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val backButton = findViewById<ImageButton>(R.id.forget_password_back_btn)
        backButton.setOnClickListener {
            // Handle back button click
            onBackPressed()
        }

        }
}