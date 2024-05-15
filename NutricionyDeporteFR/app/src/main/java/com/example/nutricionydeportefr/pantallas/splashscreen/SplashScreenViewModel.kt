package com.example.nutricionydeportefr.pantallas.splashscreen

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SplashScreenViewModel: ViewModel() {

    fun usuarioLogueado(): Boolean {
        val auth = FirebaseAuth.getInstance()
        val usuario = auth.currentUser
        return usuario != null
    }
}