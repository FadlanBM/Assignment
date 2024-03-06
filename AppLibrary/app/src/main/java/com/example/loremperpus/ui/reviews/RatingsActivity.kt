package com.example.loremperpus.ui.reviews

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RatingBar
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.core.data.source.remote.request.RatingsRequest
import com.example.loremperpus.databinding.ActivityRatingsBinding
import com.example.loremperpus.ui.auth.MainActivity
import com.example.loremperpus.util.Prefs
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class RatingsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRatingsBinding
    private val viewModel: ReviewsViewModel by viewModel()
    private lateinit var commentInput:EditText
    private lateinit var ratingBar: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRatingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolBarUpdatePeminjam,"Kembali")
        commentInput=binding.editTextComment
        ratingBar=binding.ratingBar
        val id=intent.getIntExtra("id_book",0)

        binding.buttonSubmit.setOnClickListener {
            postRatings(id)
        }
    }

    private fun postRatings(id:Int){
        val token="Bearer ${Prefs.token}"
        val body= RatingsRequest(
            id,
            commentInput.text.toString(),
            ratingBar.rating
        )
        viewModel.postComment(token,body).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    showDialog(this,"Success","Success Ratings")
                }

                State.ERROR -> {
                    toastWarning(it?.message.toString())
                }
                State.LOADING -> {

                }
            }
        }
    }

    fun showDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)

        // Set the dialog title and message
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                pushActivity(MainActivity::class.java)
            }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}