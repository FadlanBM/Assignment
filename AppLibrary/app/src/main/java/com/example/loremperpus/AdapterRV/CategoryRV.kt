package com.example.loremperpus.AdapterRV

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loremperpus.R
import com.example.loremperpus.item.ListBook
import com.example.loremperpus.item.ListCategory

class CategoryRV(val item:List<ListCategory>, val context: Context): RecyclerView.Adapter<CategoryRV.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val nameCategory=view.findViewById<TextView>(R.id.tvNameCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return CategoryRV.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_category, parent, false)
        )
    }

    override fun getItemCount(): Int {
       return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items=item[position]
        holder.nameCategory.text="#"+items.Nama
    }
}