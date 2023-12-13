package com.example.renta

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailsActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var priceTextView: TextView
    private lateinit var shortDescriptionTextView: TextView
    private lateinit var descriptionTextView: TextView
    private var pri: String? = null
    private var des: String? = null
    private var shdes: String? = null
    private var img: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        imageView = findViewById(R.id.imageView)
        priceTextView = findViewById(R.id.price)
        shortDescriptionTextView = findViewById(R.id.short_description)
        descriptionTextView = findViewById(R.id.description)

        pri = intent.getStringExtra("price")
        des = intent.getStringExtra("description")
        shdes = intent.getStringExtra("ShortDescription")
        img = intent.getStringExtra("image")


        priceTextView.text = "₱$pri"
        descriptionTextView.text = des
        shortDescriptionTextView.text = shdes
        // Load the image using Glide
        Glide.with(this)
            .load(img)
            .into(imageView)
    }
}
