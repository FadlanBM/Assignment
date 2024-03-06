package com.example.loremperpus.AdapterRV

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loremperpus.R
import com.example.loremperpus.item.ListCart
import com.example.loremperpus.util.Constants
import com.squareup.picasso.Picasso

class CartRV(val item:List<ListCart>, val context: Context): RecyclerView.Adapter<CartRV.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val title:TextView=view.findViewById(R.id.tvTitleBook)
        val author:TextView=view.findViewById(R.id.tvAuthorName)
        val releseTV:TextView=view.findViewById(R.id.tvReleseTH)
        val img:ImageView=view.findViewById(R.id.image_book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return CartRV.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_cart, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items=item[position]
        holder.title.text=items.title
        holder.author.text=items.author
        holder.releseTV.text=items.year_published.toString()
        Picasso.get().load(Constants.BASE_Image+items.img).into(holder.img)
    }
}