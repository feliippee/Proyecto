package com.example.nutricionydeportefr.pantallas.perfil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class PerfilViewModel: ViewModel() {

    //Variable para la seleccion de las opciones del bottom menu
    private var _opcionBottonMenu = MutableLiveData(3)
    var opcionBottonMenu: LiveData<Int> = _opcionBottonMenu

    //Variable para el DropdownMenu
    private val _expandir = MutableLiveData<Boolean>(false)
    val expandir: LiveData<Boolean> = _expandir

    //Variable para el alertDialog
    private val _mostrarDialog = MutableLiveData<Boolean>(false)
    val mostrarDialog: LiveData<Boolean> = _mostrarDialog

    fun setDesplegable(){
        _expandir.value = !(_expandir.value ?: false)
    }
    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }
    fun setMostrarDialog(){
        _mostrarDialog.value = !(_mostrarDialog.value ?: false)
    }
    fun cerrarSesion() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
    }

}