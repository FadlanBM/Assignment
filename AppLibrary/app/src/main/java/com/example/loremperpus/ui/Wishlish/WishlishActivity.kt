package com.example.loremperpus.ui.Wishlish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.loremperpus.AdapterRV.WishlishRV
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.databinding.ActivityWishlishBinding
import com.example.loremperpus.item.ListBook
import com.example.loremperpus.ui.MenuActivity
import com.example.loremperpus.ui.reviews.ReviewsViewModel
import com.example.loremperpus.util.Prefs
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class WishlishActivity : AppCompatActivity() {
    private lateinit var binding:ActivityWishlishBinding
    private val viewModel: WishlishViewModel by viewModel()
    private val viewModelcomment: ReviewsViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityWishlishBinding.inflate(layoutInflater)
        setToolbar(binding.toolBarUpdatePeminjam,"Kembali")
        recyclerView=binding.tvListWishlish
        setContentView(binding.root)

        getWishlist()
    }

    private fun getWishlist(){
        val token="Bearer ${Prefs.token}"
        viewModel.getWishlist(token).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val bookResponse = it.data
                    val booklist= mutableListOf<ListBook>()
                    if (bookResponse != null) {
                        val books = bookResponse.Book
                        if (books != null) {
                            for (book in books) {
                                val id=book.ID
                                val author = book.author
                                val title = book.title
                                val img=book.img
                                booklist.add(ListBook(title,author,id,img))
                            }
                            val adapter= WishlishRV(booklist,this,viewModelcomment,this)
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
        pushActivity(MenuActivity::class.java)
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        pushActivity(MenuActivity::class.java)
        super.onBackPressed()
    }
}