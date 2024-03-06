package com.example.loremperpus.ui.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.loremperpus.core.data.repository.AppRepository
import com.example.loremperpus.core.data.source.remote.request.RatingsRequest

class ReviewsViewModel(val repo: AppRepository) : ViewModel() {
    fun postComment(token:String,request: RatingsRequest)=repo.postComment(token,request).asLiveData()
    fun getComment(token:String,id:Int)=repo.getComment(token,id).asLiveData()
    fun checkComment(token:String,id:Int)=repo.checkComment(token,id).asLiveData()
}