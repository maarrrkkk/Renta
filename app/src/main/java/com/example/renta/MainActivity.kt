package com.example.renta

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
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
import com.google.android.material.snackbar.Snackbar
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
            // Check if there is an active network connection
            if (isNetworkConnected()) {

                // For demonstration purposes, let's use a delayed refresh (remove this in production)
                Handler().postDelayed({

                    // For demonstration, we'll notifyDataSetChanged()
                    adapter.notifyDataSetChanged()

                    // Hide the refresh indicator
                    swipeRefreshLayout.isRefreshing = false
                }, 2000) // Delay in milliseconds (e.g., 2000ms = 2 seconds)
            } else {
                // No internet connection, inform the user or take appropriate action
                // For example, show a Snackbar or Toast message
                showNoInternetSnackbar()
                // Hide the refresh indicator
                swipeRefreshLayout.isRefreshing = false
            }
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
                        // Parse Firebase data and populate the itemList
                        (itemList as ArrayList<Item>).add(
                            Item(
                                Objects.requireNonNull(dataSnapshot.child("location").value)
                                    .toString(),
                                Objects.requireNonNull(dataSnapshot.child("price").value)
                                    .toString(),
                                Objects.requireNonNull(dataSnapshot.child("description").value)
                                    .toString(),
                                Objects.requireNonNull(dataSnapshot.child("category").value)
                                    .toString(),
                                Objects.requireNonNull(dataSnapshot.child("name").value)
                                    .toString(),
                                Objects.requireNonNull(dataSnapshot.child("inclusions").value)
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

    // Function to handle item click in RecyclerView
    fun onItemPosition(position: Int) {
        val intent = Intent(this, DetailsActivity::class.java)

        // Pass data to the DetailsActivity
        intent.putExtra("price", itemList[position].price)
        intent.putExtra("location", itemList[position].location)
        intent.putExtra("description", itemList[position].description)
        intent.putExtra("category", itemList[position].category)
        intent.putExtra("image", itemList[position].image)
        intent.putExtra("inclusions", itemList[position].inclusions)
        intent.putExtra("name", itemList[position].name)
        startActivity(intent)
    }

    // Function to color specific text in a TextView
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

    // Function to check if the device is connected to the internet
    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    // Function to show a Snackbar when there is no internet connection
    private fun showNoInternetSnackbar() {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            "No internet connection. Please check your network settings.",
            Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }
}
