package com.example.renta

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView;
    private lateinit var price: TextView;
    private lateinit var shortDescription: TextView;
    private lateinit var description: TextView;
    private var pri: String? = null
    private var des: String? = null
    private var shdes: String? = null
    private var img: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        imageView = findViewById(R.id.image_view)
        price = findViewById(R.id.price)
        shortDescription = findViewById(R.id.short_description)
        description = findViewById(R.id.description)

        pri = intent.getStringExtra("price")
        des = intent.getStringExtra("description")
        shdes = intent.getStringExtra("ShortDescription")
        img = intent.getStringExtra("image")

        price.text = "₱$pri"
        description.text = des
        shortDescription.text = shdes
    }
}
