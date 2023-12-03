package com.example.renta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.renta.adapters.HomeAdapters
import com.example.renta.model.Item
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.renta.listeners.ItemListener
import de.hdodenhof.circleimageview.CircleImageView


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
        (itemList as ArrayList<Item>).add(Item("P5 Lagahit St.", "P2500", "2 Bedroom Hotel"))
        (itemList as ArrayList<Item>).add(Item("P5 Lagahit St.", "P2500", "2 Bedroom Hotel"))
        (itemList as ArrayList<Item>).add(Item("P5 Lagahit St.", "P2500", "2 Bedroom Hotel"))
        (itemList as ArrayList<Item>).add(Item("P5 Lagahit St.", "P2500", "2 Bedroom Hotel"))

        FirebaseDatabase.getInstance().getReference().child("images")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        (itemList as ArrayList<Item>).add(
                            Item(
                                dataSnapshot.child("location").getValue().toString(),
                                dataSnapshot.child("price").getValue().toString(),
                                dataSnapshot.child("description").getValue().toString(),
                                dataSnapshot.child("shortDescription").getValue().toString(),
                                dataSnapshot.child("image").getValue().toString()
                            )
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })


        adapter = HomeAdapters(this, itemList, ItemListener { position -> onItemPosition(position) })
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        topDealRV.layoutManager = linearLayoutManager
        topDealRV.adapter = adapter
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



}