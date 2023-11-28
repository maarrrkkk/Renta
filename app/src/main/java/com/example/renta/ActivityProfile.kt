package com.example.renta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.ImageView
import android.graphics.drawable.ColorDrawable
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ActivityProfile : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var button: FloatingActionButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        imageView = findViewById(R.id.imageView2)
        button = findViewById(R.id.floatingActionButton2)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.white)))  // Set ActionBar background color

        button.setOnClickListener{

            ImagePicker.with(this)
                .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageView.setImageURI(data?.data)
    }
}
