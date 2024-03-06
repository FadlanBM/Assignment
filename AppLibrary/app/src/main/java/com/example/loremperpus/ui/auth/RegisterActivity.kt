package com.example.loremperpus.ui.auth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.core.data.source.remote.request.RegisterRequest
import com.example.loremperpus.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.inyongtisto.myhelper.extension.getErrorBody
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModel()
    private lateinit var binding:ActivityRegisterBinding
    private lateinit var FullName:EditText
    private lateinit var Email:EditText
    private lateinit var Password:EditText
    private lateinit var ConfirmPass:EditText
    private lateinit var Phone:EditText
    private lateinit var Address:EditText
    private var firebaseAuth= FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FullName=binding.tiNamaRegister
        Email=binding.tiEmailRegister
        Password=binding.tiPasswordRegister
        ConfirmPass=binding.tiConfirmPasswordRegister
        Phone=binding.tiPhoneRegister
        Address=binding.tiAddressRegister

        binding.btnRegister.setOnClickListener {
            if(FullName.text.isEmpty()){
                FullName.error="Form Full Name is empty"
            }
            if(Email.text.isEmpty()){
                Email.error="Form Email is empty"
            }
            if(Password.text.isEmpty()){
                Password.error="Form Password is empty"
            }
            if(ConfirmPass.text.isEmpty()){
                ConfirmPass.error="Form Confirm Password is empty"
            }
            if(Phone.text.isEmpty()){
                Phone.error="Form Phone is empty"
            }
            if(Address.text.isEmpty()){
                Address.error="Form Address is empty"
            }

            if (FullName.text.isEmpty()||Email.text.isEmpty()||Password.text.isEmpty()||ConfirmPass.text.isEmpty()||Phone.text.isEmpty()||Address.text.isEmpty()){
                return@setOnClickListener
            }
            register()
        }
    }

    private fun postRegister(uid:String){
        val body= RegisterRequest(
            Address.text.toString(),
            Email.text.toString(),
            uid,
            FullName.text.toString(),
            Phone.text.toString(),
        )

        viewModel.register(body).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    showDialog(this,"Verifikasi Akun","Silahkan verifikasi akun ini terlebih dahulu")
                    binding.pbLoading.isVisible=false
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

    private fun register(){
        binding.pbLoading.isVisible=true
        firebaseAuth.createUserWithEmailAndPassword(Email.text.toString(),Password.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val userCreateProfile= userProfileChangeRequest {
                        displayName=FullName.text.toString()
                    }
                    val user=it.result.user
                    user!!.updateProfile(userCreateProfile)
                        .addOnCompleteListener {
                            firebaseAuth.currentUser?.sendEmailVerification()
                                ?.addOnSuccessListener {
                                    postRegister(user.uid)
                                }
                                ?.addOnFailureListener {
                                    binding.pbLoading.isVisible=false
                                    toastWarning(it.localizedMessage)
                                }
                        }
                        .addOnFailureListener {
                            binding.pbLoading.isVisible=false
                            toastWarning(it.localizedMessage)
                        }
                }
            }
            .addOnFailureListener {
                binding.pbLoading.isVisible=false
                toastWarning(it.localizedMessage)
            }
    }

    fun showDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)

        // Set the dialog title and message
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                pushActivity(LoginActivity::class.java)
            }

        // Create the AlertDialog object and show it
        val dialog = builder.create()
        dialog.show()
    }
}