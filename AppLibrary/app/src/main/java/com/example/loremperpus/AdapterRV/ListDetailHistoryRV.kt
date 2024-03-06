package com.example.loremperpus.AdapterRV

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.loremperpus.R
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.core.data.source.remote.request.ListLendingRequest
import com.example.loremperpus.item.ListCart
import com.example.loremperpus.item.ListDetailHistory
import com.example.loremperpus.ui.Lending.GenerateQRActivity
import com.example.loremperpus.ui.history.HistoryViewModel
import com.example.loremperpus.ui.reviews.CommentActivity
import com.example.loremperpus.ui.reviews.RatingsActivity
import com.example.loremperpus.util.CartSharePreft
import com.example.loremperpus.util.Constants
import com.example.loremperpus.util.Prefs
import com.inyongtisto.myhelper.extension.showToast
import com.squareup.picasso.Picasso

class ListDetailHistoryRV(val item:List<ListDetailHistory>, val viewModel: HistoryViewModel, val context: Context, val status:String,val lifecycleOwner: LifecycleOwner): RecyclerView.Adapter<ListDetailHistoryRV.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val title:TextView=view.findViewById(R.id.tvTitleBookHistory)
        val author:TextView=view.findViewById(R.id.tvAuthorNameHistory)
        val releseTV:TextView=view.findViewById(R.id.tvReleseThHistory)
        val img:ImageView=view.findViewById(R.id.imageBookHistory)
        val btnUlasan:Button=view.findViewById(R.id.btnUlasHistory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ListDetailHistoryRV.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_detail_history, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items=item[position]
        holder.title.text=items.title
        holder.author.text=items.author
        holder.releseTV.text=items.year_published
        checkLanding(items.ID,holder)
        Picasso.get().load(Constants.BASE_Image+items.img).into(holder.img)
/*
        holder.btnUlasan.isVisible = status != "false"
*/
        holder.btnUlasan.setOnClickListener {
            val intent=Intent(context,RatingsActivity::class.java)
            intent.putExtra("id_book",items.ID)
            context.startActivity(intent)
        }
    }

    private fun checkLanding(id:Int,holder: ViewHolder){
            val token="Bearer ${Prefs.token}"
            viewModel.checkComment(token,id).observe(lifecycleOwner) {
                when (it.state) {
                    State.SUCCESS -> {
                       holder.btnUlasan.isVisible=false
                    }

                    State.ERROR -> {
                        holder.btnUlasan.isVisible=true
                    }
                    State.LOADING -> {

                    }
                }
            }
        }
}