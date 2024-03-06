package com.example.loremperpus.ui.Lending

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.databinding.ActivityGenerateQractivityBinding
import com.example.loremperpus.ui.MenuActivity
import com.example.loremperpus.util.Prefs
import com.example.loremperpus.util.generateQRCode
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenerateQRActivity : AppCompatActivity() {
    private val viewModel: LandingBookViewModel by viewModel()
    private lateinit var binding:ActivityGenerateQractivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGenerateQractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackMenu.setOnClickListener {
            pushActivity(MenuActivity::class.java)
        }

        val id= intent.getIntExtra("id",0)
        Log.e("ids",id.toString())
        getDetailCategory(id)
    }

    private fun getDetailCategory(id:Int){
        val token="Bearer ${Prefs.token}"
        viewModel.getDetailDataLendings(token,id).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val code=it.data?.code
                    binding.tvShowCode.text=code
                    val bitmap = generateQRCode(code.toString(), 600, 600)
                    binding.imageViewQR.setImageBitmap(bitmap)
                }

                State.ERROR -> {
                    toastWarning(it?.message.toString())

                }
                State.LOADING -> {

                }
            }
        }
    }
}