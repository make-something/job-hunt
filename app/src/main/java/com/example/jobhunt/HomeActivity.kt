package com.example.jobhunt

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.jobhunt.Adapter.MyAdapter
import com.example.jobhunt.R.id.recycler_view2
import com.example.jobhunt.models.job
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Job


@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity()
{
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private val jobList = mutableListOf<job>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.recycler_view2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(jobList)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("")

        myRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                jobList.clear()
                for (snapshot in dataSnapshot.children) {
                    val data = snapshot.getValue(job::class.java)
                    data?.let { jobList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors
                Log.w("HomeActivity", "loadPost:onCancelled", databaseError.toException())
            }
        })


        // Data untuk carousel
        val items = listOf(
            CarouselItem(R.drawable.ic_architect, "Arsitek"),
            CarouselItem(R.drawable.ic_barber, "Barber"),
            CarouselItem(R.drawable.ic_office, "Office"),
            CarouselItem(R.drawable.ic_pilot, "Pilot"),
            CarouselItem(R.drawable.ic_barber, "barber"),
            CarouselItem(R.drawable.ic_office, "Office")
        )

        val adapter = CarouselAdapter(items)


        //bottom navbar//
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_home
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> true
                R.id.bottom_search -> {
                    startActivity(Intent(applicationContext, SearchActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }
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




