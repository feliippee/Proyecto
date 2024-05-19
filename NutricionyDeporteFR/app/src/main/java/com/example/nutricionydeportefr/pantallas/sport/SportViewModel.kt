package com.example.nutricionydeportefr.pantallas.sport

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.nutricionydeportefr.itemsRecycler.ItemEntrenamiento
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class SportViewModel : ViewModel() {

    //Variable para la seleccion de las opciones del bottom menu
    private var _opcionBottonMenu = MutableLiveData(1)
    var opcionBottonMenu: LiveData<Int> = _opcionBottonMenu


    // Variable para coger los datos de Firebase
    private val _entrenamientos = MutableLiveData<List<ItemEntrenamiento>>()
    val entrenamientos: LiveData<List<ItemEntrenamiento>> = _entrenamientos

    //Variable para el alertDialog
    private val _mostrarDialog = MutableLiveData<Boolean>(false)
    val mostrarDialog: LiveData<Boolean> = _mostrarDialog



    init {
        getEntrenamientos()
    }
    fun setMostrarDialog(){
        _mostrarDialog.value = !(_mostrarDialog.value ?: false)
    }


    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }

    private fun getEntrenamientos() {
        viewModelScope.launch {
            try {
                val db = Firebase.firestore
                val result = db.collection("entrenamientos").get().await()
                val entrenamientosList = result.map { document ->
                    ItemEntrenamiento(
                        id = document.id,
                        fecha = document.getString("Fecha Entrenamiento") ?: "",
                        parteCuerpo = document.getString("Parte del cuerpo") ?: "",
                        series = document.getLong("Series")?.toString() ?: "",
                        repeticiones = document.getLong("Repeticiones")?.toString() ?: "",
                        peso = document.getDouble("Peso")?.toString() ?: "",
                        ejercicios = document.getString("Ejercicios") ?: ""
                    )
                }
                _entrenamientos.value = entrenamientosList
            } catch (exception: Exception) {
                Log.w("SportViewModel", "Error getting documents.", exception)
            }
        }
    }
}



