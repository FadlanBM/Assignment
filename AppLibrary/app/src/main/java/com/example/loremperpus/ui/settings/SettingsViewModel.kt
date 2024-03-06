package com.example.loremperpus.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.loremperpus.core.data.repository.AppRepository

class SettingsViewModel(val repo: AppRepository) : ViewModel(){
    fun getmedelete(token:String)=repo.getmedelete(token).asLiveData()
}