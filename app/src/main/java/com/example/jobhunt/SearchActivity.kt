package com.example.jobhunt

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.Adapter.MyAdapter
import com.example.jobhunt.models.job
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Suppress("DEPRECATION")
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
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    jobList.clear()
                    for (snapshot in dataSnapshot.children) {
                        val data = snapshot.getValue(job::class.java)
                        data?.let { jobList.add(it) }
                    }
                    itemAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("SearchActivity", "loadPost:onCancelled", databaseError.toException())
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

            //bottom navbar//
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
            bottomNavigationView.selectedItemId = R.id.bottom_search
            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.bottom_home -> {
                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        finish()
                        true}
                    R.id.bottom_search -> true
                    R.id.bottom_profile -> {
                        startActivity(Intent(applicationContext, ProfileActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        finish()
                        true
                    }
                    else -> false
                }
            }
        }


}



