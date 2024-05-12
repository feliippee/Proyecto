package com.example.nutricionydeportefr.pantallas.alimentacion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlimentacionViewModel: ViewModel() {

    //Variable para la seleccion de las opciones del bottom menu
    private var _opcionBottonMenu = MutableLiveData(2)
    var opcionBottonMenu: LiveData<Int> = _opcionBottonMenu
    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }
}