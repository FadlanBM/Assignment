package com.example.loremperpus.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.loremperpus.AdapterRV.HistoryLendingRV
import com.example.loremperpus.core.data.source.remote.network.State
import com.example.loremperpus.databinding.FragmentHistoryBinding
import com.example.loremperpus.item.ListHistoryLending
import com.example.loremperpus.util.Prefs
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val viewModel: HistoryViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView=binding.rvListHistory
        getListHistory()
        return root
    }

    private fun getListHistory(){
        val token="Bearer ${Prefs.token}"
        viewModel.getLending(token).observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    val LendingResponse = it.data
                    val lendinglist= mutableListOf<ListHistoryLending>()
                    if (LendingResponse != null) {
                        val lending = LendingResponse.data
                        if (lending != null) {
                            for (item in lending) {
                                val id=item.ID
                                val code=item.code
                                val borrowDate=item.borrowdate
                                val returnDate=item.returnDate
                                val lastDate=item.lastdate
                                val status=item.status
                                lendinglist.add(ListHistoryLending(id,code,borrowDate,returnDate,lastDate,status))
                            }
                            val adapter= HistoryLendingRV(lendinglist,requireContext(),viewModel,viewLifecycleOwner)
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