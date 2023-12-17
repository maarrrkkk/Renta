package com.example.renta

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class ActivitySplashScreen : AppCompatActivity() {

    // Set the duration of the splash screen in milliseconds
    private var SPLASH_SCREEN_TIME: Long = 3500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the layout for the splash screen
        setContentView(R.layout.activity_splash_screen)

        // Use Handler to delay the transition to the LoginActivity
        Handler(Looper.myLooper()!!).postDelayed({
            // Start the LoginActivity after the specified splash screen time
            startActivity(Intent(this, LoginActivity::class.java))

            // Finish the splash screen activity to prevent going back
            finish()
        }, SPLASH_SCREEN_TIME)
    }
}
