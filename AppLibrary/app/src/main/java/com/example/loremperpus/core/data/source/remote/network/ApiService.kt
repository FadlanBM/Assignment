package com.example.loremperpus.core.data.source.remote.network

import com.example.loremperpus.core.data.source.remote.request.RatingsRequest
import com.example.loremperpus.core.data.source.remote.request.LandingRequest
import com.example.loremperpus.core.data.source.remote.request.ListLendingRequest
import com.example.loremperpus.core.data.source.remote.request.LoginRequest
import com.example.loremperpus.core.data.source.remote.request.RegisterRequest
import com.example.loremperpus.core.data.source.remote.response.BookDetailResponse
import com.example.loremperpus.core.data.source.remote.response.BookResponse
import com.example.loremperpus.core.data.source.remote.response.CategoryResponse
import com.example.loremperpus.core.data.source.remote.response.LendingResponse
import com.example.loremperpus.core.data.source.remote.response.LikeResponse
import com.example.loremperpus.core.data.source.remote.response.ListHistorylResponse
import com.example.loremperpus.core.data.source.remote.response.ListLending
import com.example.loremperpus.core.data.source.remote.response.LoginRespose
import com.example.loremperpus.core.data.source.remote.response.MeResponse
import com.example.loremperpus.core.data.source.remote.response.RatingsResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/register")
    suspend fun register(
        @Body login:RegisterRequest
    ): Response<ResponseBody>

    @POST("auth")
    suspend fun login(
        @Body login:LoginRequest
    ): Response<LoginRespose>

    @GET("me")
    suspend fun getme(
        @Header("Authorization") token: String,
    ): Response<MeResponse>
    @DELETE("me/delete")
    suspend fun getmedelete(
        @Header("Authorization") token: String,
    ): Response<MeResponse>

    @GET("book")
    suspend fun getBook(
        @Header("Authorization") token: String,
    ): Response<BookResponse>
    @GET("book/category/{id}")
    suspend fun getlistcategory(
        @Header("Authorization") token: String,
        @Path("id") bookID: Int
    ): Response<CategoryResponse>
    @GET("book/{id}")
    suspend fun getDetailBook(
        @Header("Authorization") token: String,
        @Path("id") bookID: Int
    ): Response<BookDetailResponse>
    @GET("lending")
    suspend fun getLending(
        @Header("Authorization") token: String,
    ): Response<ListLending>
    @POST("lending/create")
    suspend fun postDataLendings(
        @Header("Authorization") token: String,
        @Body lading:LandingRequest
    ): Response<LendingResponse>
    @GET("lending/{id}")
    suspend fun getDetailDataLendings(
        @Header("Authorization") token: String,
        @Path("id") bookID: Int
    ): Response<LendingResponse>
    @POST("lending/list")
    suspend fun postDataListLandings(
        @Header("Authorization") token: String,
        @Body landing:ListLendingRequest
    ): Response<ResponseBody>
    @GET("lending/history/{id}")
    suspend fun getDetailHistoryLending(
        @Header("Authorization") token: String,
        @Path("id") lendingID: Int
    ): Response<ListHistorylResponse>
    @DELETE("lending/delete/{id}")
    suspend fun DeletelHistoryLending(
        @Header("Authorization") token: String,
        @Path("id") lendingID: Int
    ): Response<ResponseBody>
    @POST("comment")
    suspend fun postComment(
        @Header("Authorization") token: String,
        @Body comment:RatingsRequest
    ): Response<ResponseBody>
    @GET("comment/{id}")
    suspend fun getComment(
        @Header("Authorization") token: String,
        @Path("id") bookID: Int
    ): Response<RatingsResponse>
    @GET("comment/check/{id}")
    suspend fun checkComment(
        @Header("Authorization") token: String,
        @Path("id") bookID: Int
    ): Response<ResponseBody>
    @GET("collection")
    suspend fun getWishlist(
        @Header("Authorization") token: String,
    ): Response<LikeResponse>
    @GET("collection/check/{id}")
    suspend fun checkLove(
        @Header("Authorization") token: String,
        @Path("id") bookID: Int
    ): Response<ResponseBody>
    @POST("collection/create/{id}")
    suspend fun postLove(
        @Header("Authorization") token: String,
        @Path("id") bookID: Int
    ): Response<ResponseBody>
    @DELETE("collection/delete/{id}")
    suspend fun deleteLove(
        @Header("Authorization") token: String,
        @Path("id") bookID: Int
    ): Response<ResponseBody>
    @GET("book/validateQR/{code}")
    suspend fun getBookQR(
        @Header("Authorization") token: String,
        @Path("code") code: String
    ): Response<ResponseBody>
}
