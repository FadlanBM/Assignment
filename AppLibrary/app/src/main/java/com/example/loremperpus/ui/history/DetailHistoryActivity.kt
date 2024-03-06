package com.example.loremperpus.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.loremperpus.AdapterRV.BookRV
import com.example.loremperpus.AdapterRV.CartRV
import com.example.loremperpus.AdapterRV.ListDetailHistoryRV
import com.example.loremperpus.R
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.databinding.ActivityDetailHistoryBinding
import com.example.loremperpus.item.ListBook
import com.example.loremperpus.item.ListCart
import com.example.loremperpus.item.ListDetailHistory
import com.example.loremperpus.util.Prefs
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailHistoryActivity : AppCompatActivity() {
    private val viewModel: HistoryViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding:ActivityDetailHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailHistoryBinding.inflate(layoutInflater)
        setToolbar(binding.toolBarUpdatePeminjam,"Kembali")
        setContentView(binding.root)
        recyclerView=binding.RvListHistory
        val id =intent.getIntExtra("id",0)
        getListCategory(id)
    }

    private fun getListCategory(id:Int){
        val token="Bearer ${Prefs.token}"
        val status=intent.getStringExtra("status").toString()
        viewModel.getDetailHistoryLending(token,id).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val bookResponse = it.data
                    val ladings= mutableListOf<ListDetailHistory>()
                    if (bookResponse != null) {
                        val books = bookResponse.Book
                        if (books != null) {
                            for (book in books) {
                                val id=book.ID
                                val author = book.author
                                val title = book.title
                                val img=book.img
                                val years=book.year_published
                                ladings.add(ListDetailHistory(id,author,img,years.toString(),title))
                            }
                            val adapter= ListDetailHistoryRV(ladings,viewModel,this,status,this)
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