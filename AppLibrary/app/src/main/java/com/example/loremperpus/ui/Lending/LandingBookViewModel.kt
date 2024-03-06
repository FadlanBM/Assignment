package com.example.loremperpus.ui.Lending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.loremperpus.core.data.repository.AppRepository
import com.example.loremperpus.core.data.source.remote.request.LandingRequest
import com.example.loremperpus.core.data.source.remote.request.ListLendingRequest

class LandingBookViewModel(val repo: AppRepository) : ViewModel() {
    fun getDetailBook(token:String,id:Int)=repo.getDetailBook(token,id).asLiveData()
    fun postDataLendings(token:String,request: LandingRequest)=repo.postDataLendings(token,request).asLiveData()
    fun getDetailDataLendings(token:String,id: Int)=repo.getDetailDataLendings(token,id).asLiveData()
    fun postDataListLandings(token:String,request:ListLendingRequest)=repo.postDataListLandings(token,request).asLiveData()
}