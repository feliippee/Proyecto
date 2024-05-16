package com.example.nutricionydeportefr.pantallas.sport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SportViewModel: ViewModel() {

    //Variable para la seleccion de las opciones del bottom menu
    private var _opcionBottonMenu = MutableLiveData(1)
    var opcionBottonMenu: LiveData<Int> = _opcionBottonMenu

    //Variables para controlar las progresbar
    private var _mostrarProgressBar = MutableLiveData(false)
    var mostrarProgressBar: LiveData<Boolean> = _mostrarProgressBar
    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }

}