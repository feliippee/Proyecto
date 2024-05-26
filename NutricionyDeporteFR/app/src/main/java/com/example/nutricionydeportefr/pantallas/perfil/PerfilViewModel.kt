package com.example.nutricionydeportefr.pantallas.perfil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class PerfilViewModel: ViewModel() {

    //Variable para la seleccion de las opciones del bottom menu
    private var _opcionBottonMenu = MutableLiveData(3)
    var opcionBottonMenu: LiveData<Int> = _opcionBottonMenu

    private val _nombreUsuario = MutableLiveData<String>()
    val nombreUsuario: LiveData<String> = _nombreUsuario


    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }
    fun obtenerNombreUsuario() {
        val usuario = FirebaseAuth.getInstance().currentUser
        _nombreUsuario.value = usuario?.displayName
    }

}