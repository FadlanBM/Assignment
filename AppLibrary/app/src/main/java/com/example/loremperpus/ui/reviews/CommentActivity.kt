package com.example.loremperpus.ui.reviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.loremperpus.AdapterRV.CategoryRV
import com.example.loremperpus.AdapterRV.RatingsRV
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.core.data.source.remote.request.RatingsRequest
import com.example.loremperpus.databinding.ActivityCommentBinding
import com.example.loremperpus.item.ListCategory
import com.example.loremperpus.item.ListRatings
import com.example.loremperpus.util.Prefs
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCommentBinding
    private val viewModel: ReviewsViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolBarUpdatePeminjam,"Kembali")
        recyclerView=binding.RvCommentHistory

        val id=intent.getIntExtra("id_book",0)
        getRatings(id)

    }

    private fun getRatings(id:Int){
        val token="Bearer ${Prefs.token}"
        viewModel.getComment(token,id).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val ratingsResponse = it.data
                    val ratingslist= mutableListOf<ListRatings>()
                    if (ratingsResponse != null) {
                        val ratings = ratingsResponse.data
                        if (ratings != null) {
                            for (item in ratings) {
                                val id=item.ID
                                val name = item.borrower_name
                                val message=item.message
                                val ratings=item.ratings
                                ratingslist.add(ListRatings(id,name,message,ratings))
                            }
                            val adapter= RatingsRV(ratingslist,this)
                            recyclerView.adapter=adapter
                        }
                    }
                }

                State.ERROR -> {
                    toastWarning(it?.message.toString())
                }
                State.LOADING -> {

                }
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}