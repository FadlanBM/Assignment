package com.example.loremperpus.ui.auth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.core.data.source.remote.request.LoginRequest
import com.example.loremperpus.databinding.ActivityLoginBinding
import com.example.loremperpus.ui.MenuActivity
import com.example.loremperpus.util.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModel()
    lateinit var Email:EditText
    lateinit var Password:EditText
    private lateinit var binding:ActivityLoginBinding
    private var firebaseAuth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Email=binding.tiEmailLogin
        Password=binding.tiPasswordLogin

        binding.btnLogin.setOnClickListener {
            if (Email.text.isEmpty()){
                Email.error="Email is Empty"
            }
            if (Password.text.isEmpty()){
                Password.error="Password is Empty"
            }

            if (Email.text.isEmpty()||Password.text.isEmpty()){
                return@setOnClickListener
            }
            login()
        }
        binding.tvRegisterLogin.setOnClickListener {
            pushActivity(RegisterActivity::class.java)
        }
    }
    private fun login(){
        binding.pbLoading.isVisible=true
        firebaseAuth.signInWithEmailAndPassword(Email.text.toString(),Password.text.toString())
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    val verif=firebaseAuth.currentUser?.isEmailVerified
                    if (verif==true){
                        postLogin(firebaseAuth.uid.toString())
                    }else{
                        showDialog(this,"Verifikasi Email","Email Belum Verifikasi")
                    }
                }else{
                    toastWarning("Email/Password salah ")
                    binding.pbLoading.isVisible=false
                }
            }
    }

    private fun postLogin(uid:String){
        val body=LoginRequest(
            Email.text.toString(),
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


    fun showDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)

        // Set the dialog title and message
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                binding.pbLoading.isVisible=false
            }

        // Create the AlertDialog object and show it
        val dialog = builder.create()
        dialog.show()
    }

}