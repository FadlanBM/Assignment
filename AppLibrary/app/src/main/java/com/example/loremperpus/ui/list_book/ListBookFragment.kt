package com.example.loremperpus.ui.list_book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.loremperpus.AdapterRV.BookRV
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.databinding.FragmentListBookBinding
import com.example.loremperpus.item.ListBook
import com.example.loremperpus.ui.reviews.ReviewsViewModel
import com.example.loremperpus.util.Prefs
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListBookFragment : Fragment() {

    private var _binding: FragmentListBookBinding? = null
    private val viewModel: ListBookViewModel by viewModel()
    private val viewModelcomment: ReviewsViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBookBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView=binding.rvListBarang
        getBook()

        return root
    }

    private fun getBook(){
        val token="Bearer ${Prefs.token}"
        viewModel.getBook(token).observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    val bookResponse = it.data
                    val booklist= mutableListOf<ListBook>()
                    if (bookResponse != null) {
                        val books = bookResponse.data
                        if (books != null) {
                            for (book in books) {
                                val id=book.ID
                                val author = book.author
                                val title = book.title
                                val img=book.img
                                booklist.add(ListBook(title,author,id,img))
                            }
                            val adapter= BookRV(booklist,requireContext(),viewModelcomment,viewLifecycleOwner)
                            recyclerView.adapter=adapter
                        }
                    }
                }

                State.ERROR -> {
                    toastWarning(it?.message.toString())
                }
                State.LOADING -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}