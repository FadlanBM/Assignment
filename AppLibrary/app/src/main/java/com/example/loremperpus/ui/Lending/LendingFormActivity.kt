package com.example.loremperpus.ui.Lending

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.loremperpus.AdapterRV.ListLendingRV
import com.example.loremperpus.R
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.databinding.ActivityLendingFormBinding
import com.example.loremperpus.item.ListCart
import com.example.loremperpus.util.CartSharePreft
import com.example.loremperpus.util.Prefs
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class LendingFormActivity : AppCompatActivity() {
    private val viewModel: LandingBookViewModel by viewModel()
    private lateinit var binding:ActivityLendingFormBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLendingFormBinding.inflate(layoutInflater)
        setToolbar(binding.toolBarUpdatePeminjam,"Kembali")
        setContentView(binding.root)
        recyclerView=binding.rvcartBarang
        binding.btnAddReturnDate.setOnClickListener {
            showModalReturnDate()
        }

        getDetailBook(binding)
    }

    private fun getDetailBook(binding:ActivityLendingFormBinding){
        val id= CartSharePreft(this).getId()
        val booklist= mutableListOf<ListCart>()
        for (ids in id) {
            viewModel.getDetailBook(Prefs.token,ids.toInt()).observe(this) {
                when (it.state) {
                    State.SUCCESS -> {
                        val bookResponse = it.data?.data
                        val idlist=bookResponse?.ID.toString()
                        val title=bookResponse?.title.toString()
                        val author=bookResponse?.author.toString()
                        val releseTH=bookResponse?.year_published.toString()
                        val img=bookResponse?.img.toString()
                        booklist.add(ListCart(idlist,author,img,releseTH,title))
                        val adapter=ListLendingRV(booklist,this,binding,viewModel,this)
                        recyclerView.adapter=adapter
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

    private fun showModalReturnDate() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_modal_add_return_date)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.window?.setBackgroundDrawableResource(R.drawable.background_modal)
        }
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val buttonCancel=dialog.findViewById<Button>(R.id.btnCanceldVoucerModal)
        val buttonAdd=dialog.findViewById<Button>(R.id.btnAddReturnDate)
        val datepicker=dialog.findViewById<ImageView>(R.id.toggleDateStart)
        val tiDate=dialog.findViewById<EditText>(R.id.tiReturnDate)
        datepicker.setOnClickListener {
            showDatePickerDialog1(tiDate)
        }
        buttonAdd.setOnClickListener {
            if (tiDate.text.isEmpty()){
                tiDate.error="Form Return Date is Empty"
            }else{
                binding.tvReturnDate.text=tiDate.text.toString()
                dialog.dismiss()
            }
        }
        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    fun showDatePickerDialog1(tiDate: EditText) {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, dayOfMonth)

                val currentDate = Calendar.getInstance()

                currentDate.add(Calendar.DAY_OF_MONTH, 7)

                if (selectedCalendar.after(currentDate)) {
                    toastWarning("Tanggal tidak boleh lebih dari 7 hari dari tanggal saat ini")
                } else {
                    val formattedMonth = (month + 1).toString().padStart(2, '0')
                    val formattedDay = dayOfMonth.toString().padStart(2, '0')
                    val selectedDate = "01/$formattedMonth/$year"
                    tiDate.setText(selectedDate)
                }
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}