package com.example.loremperpus.ui.Wishlish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.loremperpus.core.data.repository.AppRepository

class WishlishViewModel(val repo: AppRepository) : ViewModel() {
    fun checkLove(token:String,id:Int)=repo.checkLove(token,id).asLiveData()
    fun postLove(token:String,id:Int)=repo.postLove(token,id).asLiveData()
    fun deleteLove(token:String,id:Int)=repo.deleteLove(token,id).asLiveData()
    fun getWishlist(token:String)=repo.getWishlist(token).asLiveData()

}