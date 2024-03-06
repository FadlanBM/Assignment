package com.example.loremperpus.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.loremperpus.core.data.repository.AppRepository
import com.example.loremperpus.core.data.source.remote.request.LoginRequest
import com.example.loremperpus.core.data.source.remote.request.RegisterRequest
import com.example.loremperpus.core.data.source.remote.request.RegisterWithGoogleRequest

class AuthViewModel(val repo:AppRepository): ViewModel() {
    fun register(data:RegisterRequest)=repo.register(data).asLiveData()
    fun login(data:LoginRequest)=repo.login(data).asLiveData()
}