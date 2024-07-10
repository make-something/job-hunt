package com.example.jobhunt.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.R
import com.example.jobhunt.models.JobCard

class MyAdapter (private val jobList : List<JobCard>): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_job_card, parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = jobList[position]
        holder.nama_perusahaan.text= data.nama_perusahaan
        holder.posisi.text= data.posisi
        holder.lokasi.text= data.lokasi
        holder.keterampilan.text= data.keterampilan
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var nama_perusahaan : TextView = itemView.findViewById(R.id.tv_namaperusahaan)
        var posisi: TextView = itemView.findViewById(R.id.tvposisi)
        var lokasi: TextView = itemView.findViewById(R.id.iv_location)
        var keterampilan: TextView = itemView.findViewById(R.id.tvskilljob)
    }
}