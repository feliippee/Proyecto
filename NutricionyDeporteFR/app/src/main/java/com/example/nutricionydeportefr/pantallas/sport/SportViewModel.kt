package com.example.nutricionydeportefr.pantallas.sport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SportViewModel: ViewModel() {

    //Variable para la seleccion de las opciones del bottom menu
    private var _opcionBottonMenu = MutableLiveData(3)
    var opcionBottonMenu: LiveData<Int> = _opcionBottonMenu
    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }
}