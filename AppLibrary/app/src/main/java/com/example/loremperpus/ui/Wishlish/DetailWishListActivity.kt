package com.example.loremperpus.ui.Wishlish

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.loremperpus.AdapterRV.CategoryRV
import com.example.loremperpus.R
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.databinding.ActivityDetailWishlishBinding
import com.example.loremperpus.item.ListCategory
import com.example.loremperpus.ui.list_book.ListBookViewModel
import com.example.loremperpus.ui.reviews.CommentActivity
import com.example.loremperpus.util.CartSharePreft
import com.example.loremperpus.util.Constants
import com.example.loremperpus.util.Prefs
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toastWarning
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailWishListActivity : AppCompatActivity() {
    private val viewModel: ListBookViewModel by viewModel()
    private val viewModelLike: WishlishViewModel by viewModel()
    private lateinit var binding:ActivityDetailWishlishBinding
    private lateinit var rvListCategory:RecyclerView
    private lateinit var toggleButtonLike:ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailWishlishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolBarUpdatePeminjam,"Kembali")
        rvListCategory=binding.CategoryListRv
        toggleButtonLike=binding.toggleButtonLike
        val id=intent.getIntExtra("id_Book",0)
        CheckLove(id)
        toggleButtonLike.setOnCheckedChangeListener { buttonView, isChecked ->
            toggleButtonLike.setOnClickListener {
                if (isChecked) {
                    postLove(id)
                } else {
                    deleteLove(id)
                }
            }
        }
        getDetailBook(id)
        getListCategory(id)

        binding.addAddCart.setOnClickListener {
            var IsStatus=false
            val idExis=CartSharePreft(this).getId()
            for (i in idExis.indices){
                val ids=idExis[i]
                if (ids==id.toString()){
                    showDialogCart(this,"Warning","Book sudah terdapat di cart")
                    IsStatus=true
                }
            }
            if (!IsStatus){
                CartSharePreft(this).saveId(id.toString())
                showDialogCart(this,"Success","Success Add Cart")
            }
        }

        binding.btnToComment.setOnClickListener {
            val intent=Intent(this,CommentActivity::class.java)
            intent.putExtra("id_book",id)
            startActivity(intent)
        }
    }

    private fun postLove(id:Int){
        Log.e("id",id.toString())
        val token="Bearer ${Prefs.token}"
        viewModelLike.postLove(token,id).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    showDialogCart(this,"Success","Success Adding to Favorites")
                    toggleButtonLike.setBackgroundResource(R.drawable.heart_on)
                    toggleButtonLike.isChecked=true
                }

                State.ERROR -> {
                    toastWarning(it?.message.toString())
                    toggleButtonLike.setBackgroundResource(R.drawable.heart_off)
                    toggleButtonLike.isChecked=false
                }
                State.LOADING -> {

                }
            }
        }
    }
    private fun CheckLove(id:Int){
        val token="Bearer ${Prefs.token}"
        viewModelLike.checkLove(token,id).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    toggleButtonLike.isChecked=true
                    toggleButtonLike.setBackgroundResource(R.drawable.heart_on)
                }

                State.ERROR -> {
                    toggleButtonLike.isChecked=false
                    toggleButtonLike.setBackgroundResource(R.drawable.heart_off)
                }
                State.LOADING -> {

                }
            }
        }
    }
    private fun deleteLove(id:Int){
        val token="Bearer ${Prefs.token}"
        viewModelLike.deleteLove(token,id).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    showDialogCart(this,"Success","Success Delete to Favorites")
                    toggleButtonLike.isChecked=false
                    toggleButtonLike.setBackgroundResource(R.drawable.heart_off)
                }

                State.ERROR -> {
                    toastWarning(it?.message.toString())
                    toggleButtonLike.isChecked=true
                    toggleButtonLike.setBackgroundResource(R.drawable.heart_on)
                }
                State.LOADING -> {

                }
            }
        }
    }


    private fun getListCategory(id:Int){
        binding.RLDetailView.isVisible=false
        val token="Bearer ${Prefs.token}"
        viewModel.getlistcategory(token,id).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val bookResponse = it.data
                    val booklist= mutableListOf<ListCategory>()
                    if (bookResponse != null) {
                        val books = bookResponse.Categories
                        if (books != null) {
                            for (book in books) {
                                val id=book.ID
                                val name = book.name
                                booklist.add(ListCategory(id,name))
                            }
                            val adapter=CategoryRV(booklist,this)
                            rvListCategory.adapter=adapter
                        }
                    }
                }

                State.ERROR -> {
                    toastWarning(it?.message.toString())
                    binding.RLDetailView.isVisible=true
                }
                State.LOADING -> {

                }
            }
        }
    }

    private fun getDetailBook(id:Int){
        val token="Bearer ${Prefs.token}"
        viewModel.getDetailBook(token,id).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val bookResponse = it.data?.data
                    val title=bookResponse?.title
                    val author=bookResponse?.author
                    val desc=bookResponse?.description
                    val img=bookResponse?.img
                    binding.bookTitle.text=title
                    binding.bookAuthorTv.text=author
                    binding.bookDesc.text=desc
                    Picasso.get().load(Constants.BASE_Image+img).into(binding.bookImage)
                    binding.RLDetailView.isVisible=true
                }

                State.ERROR -> {
                    toastWarning(it?.message.toString())
                    binding.RLDetailView.isVisible=true
                }
                State.LOADING -> {

                }
            }
        }
    }

    fun showDialogCart(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)

        // Set the dialog title and message
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
            }

        // Create the AlertDialog object and show it
        val dialog = builder.create()
        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        pushActivity(WishlishActivity::class.java)
    }
}