package com.example.loremperpus.ui.menu_settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loremperpus.R
import com.example.loremperpus.databinding.ActivityResetPassswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showToast

class ResetPassswordActivity : AppCompatActivity() {
    private lateinit var binding:ActivityResetPassswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityResetPassswordBinding.inflate(layoutInflater)
        setToolbar(binding.toolBarResetPass,"Kembali")
        setContentView(binding.root)


        binding.btnResetPass.setOnClickListener {
            if (binding.tiPasswordOld.text.toString().isEmpty()){
                binding.tiPasswordOld.error="Form Password Old masih kosong"
            }
            if (binding.tiPasswordNew.text.toString().isEmpty()){
                binding.tiPasswordOld.error="Form Password New masih kosong"
            }
            if (binding.tiPasswordNewConfirm.text.toString().isEmpty()){
                binding.tiPasswordOld.error="Form Password New Confirm masih kosong"
            }

            if (binding.tiPasswordOld.text.toString().isEmpty()||binding.tiPasswordNew.text.toString().isEmpty()||binding.tiPasswordNewConfirm.text.toString().isEmpty()) return@setOnClickListener

            if (binding.tiPasswordNew.text.toString()!=binding.tiPasswordNewConfirm.text.toString()) binding.tiPasswordNewConfirm.error="Password Confirm password tidak sama"

            changePasswordWithValidation(binding.tiPasswordOld.text.toString(),binding.tiPasswordNew.text.toString())
        }


    }

    fun changePasswordWithValidation(oldPassword: String, newPassword: String) {
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider.getCredential(user!!.email!!, oldPassword)

        user.reauthenticate(credential)
            .addOnCompleteListener { reauthTask ->
                if (reauthTask.isSuccessful) {
                    user.updatePassword(newPassword)
                        .addOnCompleteListener { updatePasswordTask ->
                            if (updatePasswordTask.isSuccessful) {
                                showToast("Password berhasil diperbarui")
                            } else {
                                showToast("Gagal memperbarui password baru: ${updatePasswordTask.exception?.message}")
                            }
                        }
                } else {
                    showToast("Gagal reautentikasi pengguna: ${reauthTask.exception?.message}")
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}