package com.example.nutricionydeportefr.pantallas.sport

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.nutricionydeportefr.itemsRecycler.ItemEntrenamiento
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class SportViewModel : ViewModel() {

    //Variable para la seleccion de las opciones del bottom menu
    private var _opcionBottonMenu = MutableLiveData(1)
    var opcionBottonMenu: LiveData<Int> = _opcionBottonMenu


    // Variable para coger los datos de Firebase
    private val _entrenamientos = MutableLiveData<List<ItemEntrenamiento>>()
    val entrenamientos: LiveData<List<ItemEntrenamiento>> = _entrenamientos

    init {

           getEntrenamientos()

    }

    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }

    private  fun getEntrenamientos() {
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
                        pesoInicial = document.getDouble("Peso Inicial")?.toString() ?: "",
                        pesoFinal = document.getDouble("Peso Final")?.toString() ?: "",
                        ejercicios = document.getString("Ejercicios") ?: ""
                    )
                }
                _entrenamientos.value = entrenamientosList
            } catch (exception: Exception) {
                Log.w("SportViewModel", "Error getting documents.", exception)
            }
        }
    }

    fun deleteEntrenamiento(itemEntrenamiento: ItemEntrenamiento) {
        viewModelScope.launch {
            try {
                val db = Firebase.firestore
                db.collection("entrenamientos").document(itemEntrenamiento.id).delete().await()
                getEntrenamientos() // Refresh the list after deletion
            } catch (exception: Exception) {
                Log.w("SportViewModel", "Error deleting document.", exception)
            }
        }
    }
}



