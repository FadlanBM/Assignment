package com.example.loremperpus.AdapterRV

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.loremperpus.R
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.item.ListBook
import com.example.loremperpus.item.ListSetting
import com.example.loremperpus.ui.Wishlish.WishlishActivity
import com.example.loremperpus.ui.auth.MainActivity
import com.example.loremperpus.ui.menu_settings.ResetPassswordActivity
import com.example.loremperpus.ui.settings.SettingsViewModel
import com.example.loremperpus.util.CartSharePreft
import com.example.loremperpus.util.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.inyongtisto.myhelper.extension.toastWarning

class AdapterListSetting(private val settingsList: List<ListSetting>,val context: Context,val viewModel: SettingsViewModel,val lifecycleOwner: LifecycleOwner):RecyclerView.Adapter<AdapterListSetting.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewSettingTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.textViewSettingDescription)
        val aksi: CardView =itemView.findViewById(R.id.btn_aksi_settings)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_setting, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val setting = settingsList[position]
        holder.titleTextView.text = setting.title
        holder.descriptionTextView.text = setting.description
        holder.aksi.setOnClickListener {
            if (setting.title=="Logout"){
                showAlertDialogLogout("Peringatan","Apakah anda akan Logout ?")
            }
            if (setting.title=="Delete Akun"){
                showAlertDialogDelete("Peringatan","Apakah anda akan Logout ?")
            }
            if (setting.title=="Reset Password"){
                context.startActivity(Intent(context,ResetPassswordActivity::class.java))
            }
            if (setting.title=="Wishlist"){
                context.startActivity(Intent(context,WishlishActivity::class.java))
            }
        }
    }

    override fun getItemCount(): Int {
        return settingsList.size
    }

    fun showAlertDialogLogout(ttl:String,msg:String) {
        val alertDialogBuilder = AlertDialog.Builder(context)

        alertDialogBuilder.setTitle(ttl)

        alertDialogBuilder.setMessage(msg)

        alertDialogBuilder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            FirebaseAuth.getInstance().signOut()
            CartSharePreft(context).clearCart()
            context.startActivity(Intent(context,MainActivity::class.java))
        })

        alertDialogBuilder.setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    fun showAlertDialogDelete(ttl:String,msg:String) {
        val alertDialogBuilder = AlertDialog.Builder(context)

        alertDialogBuilder.setTitle(ttl)

        alertDialogBuilder.setMessage(msg)

        alertDialogBuilder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            deleteMe()
        })

        alertDialogBuilder.setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    fun showAlertSuccessDeleteDelete(ttl:String,msg:String) {
        val alertDialogBuilder = AlertDialog.Builder(context)

        alertDialogBuilder.setTitle(ttl)

        alertDialogBuilder.setMessage(msg)

        alertDialogBuilder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            context.startActivity(Intent(context,MainActivity::class.java))
        })

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun deleteMe(){
        val token="Bearer ${Prefs.token}"
        viewModel.getmedelete(token).observe(lifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                    user?.delete()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                showAlertSuccessDeleteDelete("Success","Berbasil delete akun anda harus Logout")
                            } else {
                                println("Gagal menghapus akun: ${task.exception?.message}")
                            }
                        }
                }

                State.ERROR -> {
                    Toast.makeText(context, it?.message.toString(), Toast.LENGTH_SHORT).show()
                }
                State.LOADING -> {

                }
            }
        }
    }
}