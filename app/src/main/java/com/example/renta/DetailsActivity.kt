package com.example.renta

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide


class DetailsActivity : AppCompatActivity() {

    private val phoneNumber = "09355656903" // Replace with the desired phone number
    private lateinit var callBtn: Button
    private val PERMISSION_CODE = 100

    private lateinit var imageView: ImageView
    private lateinit var priceTextView: TextView
    private lateinit var shortDescriptionTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var locationTextView: TextView

    private var pri: String? = null
    private var des: String? = null
    private var shdes: String? = null
    private var img: String? = null
    private var loc: String? = null

    fun onBackButtonClick(view: View?) {
        onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        callBtn = findViewById(R.id.Call_btn)

        imageView = findViewById(R.id.imageView)
        priceTextView = findViewById(R.id.price)
        shortDescriptionTextView = findViewById(R.id.short_description)
        descriptionTextView = findViewById(R.id.description)
        locationTextView = findViewById(R.id.location)


        callBtn.setOnClickListener {
            makePhoneCall()
        }

        pri = intent.getStringExtra("price")
        des = intent.getStringExtra("description")
        shdes = intent.getStringExtra("ShortDescription")
        img = intent.getStringExtra("image")
        loc = intent.getStringExtra("location")

        priceTextView.text = "₱$pri"
        descriptionTextView.text = des
        shortDescriptionTextView.text = shdes
        locationTextView.text = loc

        // Load the image using Glide
        Glide.with(this)
            .load(img)
            .placeholder(R.drawable.room)
            .into(imageView)
    }

    private fun makePhoneCall() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phoneNumber")

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivity(intent)
        } else {
            // Handle the case where the user doesn't have the CALL_PHONE permission.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                PERMISSION_CODE
            )
        }
    }
}