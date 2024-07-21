package com.example.jobhunt

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.Adapter.MyAdapter
import com.example.jobhunt.models.job
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchActivity : AppCompatActivity() {
    

        private lateinit var recyclerView: RecyclerView
        private lateinit var itemAdapter: MyAdapter
        private val jobList = mutableListOf<job>()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_search)

            val searchView = findViewById<SearchView>(R.id.searchView)
            recyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)

            itemAdapter = MyAdapter(jobList)
            recyclerView.adapter = itemAdapter

            // Initialize Firebase Realtime Database
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("job")

            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    jobList.clear()
                    for (snapshot in dataSnapshot.children) {
                        val data = snapshot.getValue(job::class.java)
                        data?.let { jobList.add(it) }
                    }
                    itemAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException())
                }
            })

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    itemAdapter.filter.filter(newText)
                    return false
                }
            })
        }


}



