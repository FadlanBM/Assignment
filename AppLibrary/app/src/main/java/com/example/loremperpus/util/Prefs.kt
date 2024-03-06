package com.example.loremperpus.util


import com.chibatching.kotpref.KotprefModel

object Prefs :KotprefModel() {
    var token by stringPref("")

   /* fun setUser(data: Peminjam?) {
        user = data.toJson()
    }*/
}