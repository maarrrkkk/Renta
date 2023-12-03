package com.example.renta

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.renta.adapters.HomeAdapters
import com.example.renta.listeners.ItemListener
import com.example.renta.model.Item
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentHome : Fragment() {

    private lateinit var topDealRV: RecyclerView
    private lateinit var adapter: HomeAdapters
    private lateinit var itemList: List<Item>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        topDealRV = view.findViewById(R.id.top_deal_RV)

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


        adapter = HomeAdapters(requireContext(), itemList, ItemListener { position -> onItemPosition(position) })
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        topDealRV.layoutManager = linearLayoutManager
        topDealRV.adapter = adapter

        return view
    }

    fun onItemPosition(position: Int) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra("price", itemList[position].price)
        intent.putExtra("location", itemList[position].location)
        intent.putExtra("description", itemList[position].description)
        intent.putExtra("ShortDescription", itemList[position].shortDescription)
        intent.putExtra("image", itemList[position].image)
        startActivity(intent)
    }

}