package com.example.jobhunt.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.R
import com.example.jobhunt.models.job

class MyAdapter (private val jobList : List<job>): RecyclerView.Adapter<MyAdapter.ItemViewHolder>(), Filterable{
    private var filteredItemList = jobList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_job_card, parent,false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = jobList[position]
        holder.nama_perusahaan.text= data.company
        holder.posisi.text= data.position
        holder.lokasi.text= data.location

    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var nama_perusahaan : TextView = itemView.findViewById(R.id.tv_namaperusahaan)
        var posisi: TextView = itemView.findViewById(R.id.tvposisi)
        var lokasi: TextView = itemView.findViewById(R.id.iv_location)

    }
    override fun getItemCount()= filteredItemList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val query = charSequence?.toString()?.toLowerCase() ?: ""
                filteredItemList = if (query.isEmpty()) {
                    jobList
                } else {
                    jobList.filter {
                        it.company.toLowerCase().contains(query) || it.position.toLowerCase().contains(query)
                    }
                }
                return FilterResults().apply { values = filteredItemList }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                filteredItemList = filterResults?.values as List<job>
                notifyDataSetChanged()
            }
        }
    }
}