package com.example.nutricionydeportefr.pantallas.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class HomeViewModel : ViewModel() {

    //Variable para la seleccion de las opciones del bottom menu
    private var _opcionBottonMenu = MutableLiveData(0)
    var opcionBottonMenu: LiveData<Int> = _opcionBottonMenu

    private val _expandir = MutableLiveData(false)
    val expandir: LiveData<Boolean> = _expandir

    private val _objetivoMarcado = MutableLiveData<String>()
    val objetivoMarcado: LiveData<String> = _objetivoMarcado


    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }
    fun setObjetivoMarcado(objetivoMarcado: String) {
        _objetivoMarcado.value = objetivoMarcado
    }
    fun setDesplegable() {
        _expandir.value = !(_expandir.value ?: false)
    }

}