package com.example.nutricionydeportefr.pantallas.home


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    //Variable para la seleccion de las opciones del bottom menu
    private var _opcionBottonMenu = MutableLiveData(0)
    var opcionBottonMenu: LiveData<Int> = _opcionBottonMenu
    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }



}