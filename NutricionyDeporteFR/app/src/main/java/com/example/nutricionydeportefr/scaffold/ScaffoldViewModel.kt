package com.example.nutricionydeportefr.scaffold

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ScaffoldViewModel: ViewModel() {

    //Variable para el DropdownMenu
    private val _expandir = MutableLiveData<Boolean>(false)
    val expandir: LiveData<Boolean> = _expandir

    //Variable para el alertDialog
    private val _mostrarDialog = MutableLiveData<Boolean>(false)
    val mostrarDialog: LiveData<Boolean> = _mostrarDialog

    //funcion para cambiar los estados
    fun setMostrarDialog(){
        _mostrarDialog.value = !(_mostrarDialog.value ?: false)
    }
    fun setDesplegable(){
        _expandir.value = !(_expandir.value ?: false)
    }

    //Funcion para cerrar la sesion
    fun cerrarSesion() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()

    }
}