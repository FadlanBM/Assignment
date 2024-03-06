package com.example.loremperpus.core.data.source.remote

import com.example.loremperpus.core.data.source.remote.network.ApiService
import com.example.loremperpus.core.data.source.remote.request.RatingsRequest
import com.example.loremperpus.core.data.source.remote.request.LandingRequest
import com.example.loremperpus.core.data.source.remote.request.ListLendingRequest
import com.example.loremperpus.core.data.source.remote.request.LoginRequest
import com.example.loremperpus.core.data.source.remote.request.RegisterRequest

class RemoteDataSouce(private val api:ApiService) {
    suspend fun register(data:RegisterRequest)=api.register(data)
    suspend fun login(data:LoginRequest)=api.login(data)
    suspend fun getme(token:String)=api.getme(token)
    suspend fun getmedelete(token:String)=api.getmedelete(token)
    suspend fun getBook(token:String)=api.getBook(token)
    suspend fun getlistcategory(token:String,id:Int)=api.getlistcategory(token,id)
    suspend fun getDetailBook(token:String,id:Int)=api.getDetailBook(token,id)
    suspend fun getLending(token:String)=api.getLending(token)
    suspend fun postDataLendings(token:String,request:LandingRequest)=api.postDataLendings(token,request)
    suspend fun getDetailDataLendings(token:String,id:Int)=api.getDetailDataLendings(token,id)
    suspend fun postDataListLandings(token:String,request:ListLendingRequest)=api.postDataListLandings(token,request)
    suspend fun getDetailHistoryLending(token:String,id:Int)=api.getDetailHistoryLending(token,id)
    suspend fun DeletelHistoryLending(token:String,id:Int)=api.DeletelHistoryLending(token,id)
    suspend fun postComment(token:String,request:RatingsRequest)=api.postComment(token,request)
    suspend fun getComment(token:String,id:Int)=api.getComment(token,id)
    suspend fun checkComment(token:String,id:Int)=api.checkComment(token,id)
    suspend fun checkLove(token:String,id:Int)=api.checkLove(token,id)
    suspend fun postLove(token:String,id:Int)=api.postLove(token,id)
    suspend fun deleteLove(token:String,id:Int)=api.deleteLove(token,id)
    suspend fun getWishlist(token:String)=api.getWishlist(token)
    suspend fun getBookQR(token:String,code:String)=api.getBookQR(token,code)
}