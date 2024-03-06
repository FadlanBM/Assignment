package com.example.loremperpus.AdapterRV

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.loremperpus.R
import com.example.loremperpus.item.ListCart
import com.example.loremperpus.item.ListDetailHistory
import com.example.loremperpus.item.ListRatings
import com.example.loremperpus.ui.reviews.CommentActivity
import com.example.loremperpus.ui.reviews.RatingsActivity
import com.example.loremperpus.util.Constants
import com.squareup.picasso.Picasso

class RatingsRV(val item:List<ListRatings>, val context: Context): RecyclerView.Adapter<RatingsRV.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val borrowers:TextView=view.findViewById(R.id.textViewUserName)
        val ratingsbar:RatingBar=view.findViewById(R.id.ratingBar)
        val comment:TextView=view.findViewById(R.id.textViewComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return RatingsRV.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_comment, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items=item[position]
        holder.borrowers.text=items.borrower_name
        holder.comment.text=items.message
        holder.ratingsbar.rating=items.ratings.toFloat()
    }
}