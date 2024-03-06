package com.example.loremperpus.ui.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.loremperpus.R
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.core.data.source.remote.request.LoginRequest
import com.example.loremperpus.core.data.source.remote.request.RegisterRequest
import com.example.loremperpus.core.data.source.remote.request.RegisterWithGoogleRequest
import com.example.loremperpus.databinding.ActivityMainBinding
import com.example.loremperpus.ui.MenuActivity
import com.example.loremperpus.util.Constants
import com.example.loremperpus.util.Prefs
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModel()
    private lateinit var binding:ActivityMainBinding
    private var firebaseAuth=FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null && currentUser.isEmailVerified) {
            binding.pbLoading.isVisible=true
            postLogin(currentUser.uid,currentUser.email.toString())
        }
    }
    companion object{
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            pushActivity(LoginActivity::class.java)
        }

        binding.btnRegister.setOnClickListener {
            pushActivity(RegisterActivity::class.java)
        }
    }

    private fun postLogin(uid:String,email:String){
        val body= LoginRequest(
            email,
            uid
        )
        Log.e("postLogin",uid)
        viewModel.login(body).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val token=it?.data
                    Prefs.token=token.toString()
                    binding.pbLoading.isVisible=false
                    pushActivity(MenuActivity::class.java)
                }

                State.ERROR -> {
                    toastWarning(it?.message.toString())
                    binding.pbLoading.isVisible=false
                }
                State.LOADING -> {

                }
            }
        }
    }
}