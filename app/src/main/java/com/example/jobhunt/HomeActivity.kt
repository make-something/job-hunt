package com.example.jobhunt

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.Adapter.MyAdapter
import com.example.jobhunt.R.id.recycler_view2
import com.example.jobhunt.models.JobCard
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Job


class HomeActivity : AppCompatActivity()
{
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private val jobList = mutableListOf<JobCard>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.recycler_view2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(jobList)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("job")

        myRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                jobList.clear()
                for (snapshot in dataSnapshot.children) {
                    val data = snapshot.getValue(JobCard::class.java)
                    data?.let { jobList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors
                Log.w("HomeActivity", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }
}




