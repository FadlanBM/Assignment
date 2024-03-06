package com.example.loremperpus.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loremperpus.R
import com.example.loremperpus.databinding.ActivityShowQractivityBinding
import com.example.loremperpus.util.generateQRCode
import com.inyongtisto.myhelper.extension.setToolbar

class ShowQRActivity : AppCompatActivity() {
    private lateinit var binding:ActivityShowQractivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityShowQractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolBarUpdatePeminjam,"Kembali")

        val code =intent.getStringExtra("code")
        val bitmap = generateQRCode(code.toString(), 600, 600)
        binding.imageViewQR.setImageBitmap(bitmap)

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}