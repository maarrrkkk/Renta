package com.example.renta.forgotpassword

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.renta.R
import com.google.android.material.button.MaterialButton

class ActivitySuccessPasswordChanged : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_password_changed)

        val backButton = findViewById<MaterialButton>(R.id.forget_password_next_btn)
        backButton.setOnClickListener {
            // Handle back button click
            onBackPressed()
        }
    }
}