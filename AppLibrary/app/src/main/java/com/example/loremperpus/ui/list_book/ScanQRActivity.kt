package com.example.loremperpus.ui.list_book

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.loremperpus.R
import com.example.loremperpus.databinding.ActivityScanQractivityBinding

class ScanQRActivity : AppCompatActivity() {
    private lateinit var binding:ActivityScanQractivityBinding
    private  lateinit var codeScanner:CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityScanQractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private  fun startScan(){
        val scannerview: CodeScannerView =binding.scannerView
        codeScanner= CodeScanner(this,scannerview)
        codeScanner.camera= CodeScanner.CAMERA_BACK
        codeScanner.formats= CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode= AutoFocusMode.SAFE
        codeScanner.scanMode= ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled=false
        codeScanner.isFlashEnabled=false

        codeScanner.decodeCallback= DecodeCallback {
            runOnUiThread{
                if (intent.getStringExtra("status")=="updateBarang"){
                    val intent= Intent(this, DetailBookActivity::class.java)
                    intent.putExtra("id_Book",it.toString())
                    startActivity(intent)
                } else{

                }
            }
        }
        codeScanner.errorCallback= ErrorCallback {
            runOnUiThread{
                Toast.makeText(this,"Error Scann ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
        scannerview.setOnClickListener{
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==123){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Camera Permition Grade", Toast.LENGTH_SHORT).show()
                startScan()
            }else{
                Toast.makeText(this,"Camera Permition Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (:: codeScanner.isInitialized){
            codeScanner?.startPreview()
        }
    }

    override fun onPause() {
        if (:: codeScanner.isInitialized){
            codeScanner?.releaseResources()
        }
        super.onPause()
    }

}