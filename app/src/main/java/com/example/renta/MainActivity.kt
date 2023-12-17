package com.example.renta

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val PERMISSION_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            // Your logic to refresh the data (e.g., fetch new data from the server)
            // For demonstration purposes, let's use a delayed refresh (remove this in production)
            Handler().postDelayed({
                // Update your RecyclerView data here
                // For demonstration, we'll notifyDataSetChanged()
                adapter.notifyDataSetChanged()

                // Hide the refresh indicator
                swipeRefreshLayout.isRefreshing = false
            }, 2000) // Delay in milliseconds (e.g., 2000ms = 2 seconds)
        }

        topDealRV = findViewById(R.id.top_deal_RV)

        // Set click listener for the profile image
        val profileImage: CircleImageView = findViewById(R.id.profile_image)
        profileImage.setOnClickListener {
            val intent = Intent(this@MainActivity, ActivityProfile::class.java)
            startActivity(intent)
        }

        // PERMISSION ACCESS
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_CODE
            )
        }

        itemList = ArrayList()

        // Firebase data retrieval
        FirebaseDatabase.getInstance().reference.child("images")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        (itemList as ArrayList<Item>).add(
                            Item(
                                Objects.requireNonNull(dataSnapshot.child("location").value)
                                    .toString(),
                                Objects.requireNonNull(dataSnapshot.child("price").value)
                                    .toString(),
                                Objects.requireNonNull(dataSnapshot.child("description").value)
                                    .toString(),
                                Objects.requireNonNull(dataSnapshot.child("shortDescription").value)
                                    .toString(),
                                Objects.requireNonNull(dataSnapshot.child("image").value)
                                    .toString(),
                            )
                        )
                    }

                    // Initialize and set up the adapter after data retrieval
                    adapter = HomeAdapters(
                        this@MainActivity,
                        itemList,
                        ItemListener { position -> onItemPosition(position) })
                    val linearLayoutManager = LinearLayoutManager(this@MainActivity)
                    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
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
        // Get the resolved color value for the color resource
        val yellowColor = ContextCompat.getColor(textView.context, R.color.yellow2)

        // Set the color for the first three characters (RENT) to white
        spannableString.setSpan(
            ForegroundColorSpan(yellowColor),
            0,
            4,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Set the color for the last character (A) to yellow
        spannableString.setSpan(
            ForegroundColorSpan(Color.BLACK),
            4,
            5,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Set the SpannableString to the TextView
        textView.text = spannableString
    }
}
