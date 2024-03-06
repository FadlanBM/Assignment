package com.example.loremperpus.AdapterRV

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.loremperpus.R
import com.example.loremperpus.core.data.source.models.Book
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.item.ListBook
import com.example.loremperpus.ui.Wishlish.DetailWishListActivity
import com.example.loremperpus.ui.list_book.DetailBookActivity
import com.example.loremperpus.ui.reviews.ReviewsViewModel
import com.example.loremperpus.util.Constants
import com.example.loremperpus.util.Prefs
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope

class WishlishRV(val item:List<ListBook>, val context: Context, val reviewsViewModel: ReviewsViewModel, val lifecycleOwner: LifecycleOwner): RecyclerView.Adapter<WishlishRV.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val Img: ImageView =view.findViewById(R.id.imgBook)
        val titlebook: TextView =view.findViewById(R.id.titleBookTV)
        val authorbook: TextView =view.findViewById(R.id.bookAuthorTV)
        val ratings: TextView =view.findViewById(R.id.bookRatingTV)
        val btnDetail: LinearLayout =view.findViewById(R.id.btnDetailBook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_book,parent,false))
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items=item[position]
        holder.titlebook.text=items.title
        holder.authorbook.text=items.author
        holder.btnDetail.setOnClickListener {
            val intent=Intent(context, DetailWishListActivity::class.java)
            Log.e("idToken",items.ID.toString())
            intent.putExtra("id_Book",items.ID)
            context.startActivity(intent)
        }
        Picasso.get().load(Constants.BASE_Image+items.img).into(holder.Img)
        getRatings(items.ID,holder)

    }

    private fun getRatings(id:Int,holder: ViewHolder){
        val token="Bearer ${Prefs.token}"
        reviewsViewModel.getComment(token,id).observe(lifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    val ratingsResponse = it.data
                    val ratingslist = mutableListOf<Int>()
                    val borrowlist = mutableListOf<Int>()
                    if (ratingsResponse != null) {
                        val ratingsData = ratingsResponse.data
                        if (ratingsData != null) {
                            for (item in ratingsData) {
                                val ratings = item.ratings
                                val borrow=item.BorrowID
                                ratingslist.add(ratings)
                                borrowlist.add(borrow)
                            }
                            val sumRatings = ratingslist.sum()
                            val sumBorrow = borrowlist.sum()
                            holder.ratings.text = (sumRatings.toDouble()/sumBorrow.toDouble()).toString()
                        }
                    }
                }

                State.ERROR -> {
                    Log.e("Error",it?.message.toString())
                }
                State.LOADING -> {

                }
            }
        }
    }
}