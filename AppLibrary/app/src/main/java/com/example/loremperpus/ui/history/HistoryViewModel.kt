package com.example.loremperpus.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.loremperpus.core.data.repository.AppRepository
import com.example.loremperpus.core.data.source.remote.request.RatingsRequest

class HistoryViewModel(val repo: AppRepository) : ViewModel() {
    fun getDetailHistoryLending(token:String,id:Int)=repo.getDetailHistoryLending(token,id).asLiveData()
    fun getLending(token:String)=repo.getLending(token).asLiveData()
    fun checkComment(token:String,id:Int)=repo.checkComment(token,id).asLiveData()
    fun DeletelHistoryLending(token:String,id:Int)=repo.DeletelHistoryLending(token,id).asLiveData()
}