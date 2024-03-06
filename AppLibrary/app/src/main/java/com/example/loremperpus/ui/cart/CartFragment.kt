package com.example.loremperpus.ui.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loremperpus.AdapterRV.CartRV
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.databinding.FragmentCartBinding
import com.example.loremperpus.item.ListCart
import com.example.loremperpus.ui.Lending.LendingFormActivity
import com.example.loremperpus.util.CartSharePreft
import com.example.loremperpus.util.Prefs
import com.example.loremperpus.util.SwipeToDeleteCallback
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val viewModel: CartViewModel by viewModel()
    private lateinit var recyclerView:RecyclerView
    private lateinit var btnPeminjaman:Button

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView=binding.tvListCart
        btnPeminjaman=binding.btnPeminjaman

        val ids=CartSharePreft(requireContext()).getId()
        if (ids.isEmpty()){
            btnPeminjaman.isVisible=false
        }

        binding.btnPeminjaman.setOnClickListener {
            startActivity(Intent(requireContext(),LendingFormActivity::class.java))
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                CartSharePreft(requireContext()).deleteData(position)
                val ids=CartSharePreft(requireContext()).getId()
                if (ids.isEmpty()){
                    btnPeminjaman.isVisible=false
                }
                getDetailBook()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        getDetailBook()
        return root
    }

    private fun getDetailBook(){
        val id= CartSharePreft(requireContext()).getId()
        val booklist= mutableListOf<ListCart>()
        for (ids in id) {
            Log.e("potition1",ids)
            viewModel.getDetailBook(Prefs.token,ids.toInt()).observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    val bookResponse = it.data?.data
                    val id=bookResponse?.ID.toString()
                    val title=bookResponse?.title.toString()
                    val author=bookResponse?.author.toString()
                    val releseTH=bookResponse?.year_published.toString()
                    val img=bookResponse?.img.toString()
                    booklist.add(ListCart(id,author,img,releseTH,title))
                    val adapter=CartRV(booklist,requireContext())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}