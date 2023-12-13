package com.example.renta

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.renta.adapters.HomeAdapters
import com.example.renta.listeners.ItemListener
import com.example.renta.model.Item
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Objects

class MainActivity : AppCompatActivity() {

    private lateinit var topDealRV: RecyclerView
    private lateinit var adapter: HomeAdapters
    private lateinit var itemList: MutableList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topDealRV = findViewById(R.id.top_deal_RV)

        // Set click listener for the profile image
        val profileImage: CircleImageView = findViewById(R.id.profile_image)
        profileImage.setOnClickListener {
            val intent = Intent(this@MainActivity, ActivityProfile::class.java)
            startActivity(intent)
        }

        itemList = ArrayList()

        // Firebase data retrieval
        FirebaseDatabase.getInstance().reference.child("images")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        (itemList as ArrayList<Item>).add(
                            Item(
                                Objects.requireNonNull(dataSnapshot.child("location").value).toString(),
                                Objects.requireNonNull(dataSnapshot.child("price").value).toString(),
                                Objects.requireNonNull(dataSnapshot.child("description").value).toString(),
                                Objects.requireNonNull(dataSnapshot.child("shortDescription").value).toString(),
                                Objects.requireNonNull(dataSnapshot.child("image").value).toString(),
                            )
                        )
                    }

                    // Initialize and set up the adapter after data retrieval
                    adapter = HomeAdapters(this@MainActivity, itemList, ItemListener { position -> onItemPosition(position) })
                    val linearLayoutManager = LinearLayoutManager(this@MainActivity)
                    linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                    topDealRV.layoutManager = linearLayoutManager
                    topDealRV.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle onCancelled
                }
            })

        // Color the "RENTA" text with different colors
        val rentaTextView: TextView = findViewById(R.id.RENTA_HEADER)
        colorTextInTextView(rentaTextView)
    }

    fun onItemPosition(position: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("price", itemList[position].price)
        intent.putExtra("location", itemList[position].location)
        intent.putExtra("description", itemList[position].description)
        intent.putExtra("ShortDescription", itemList[position].shortDescription)
        intent.putExtra("image", itemList[position].image)
        startActivity(intent)
    }

    private fun colorTextInTextView(textView: TextView) {
        val fullText = "RENTA"
        val spannableString = SpannableString(fullText)

        // Set the color for the first three characters (RENT) to white
        spannableString.setSpan(ForegroundColorSpan(Color.BLACK), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set the color for the last character (A) to yellow
        spannableString.setSpan(ForegroundColorSpan(Color.YELLOW), 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set the SpannableString to the TextView
        textView.text = spannableString
    }
}
