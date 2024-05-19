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

    //Variable para la expansion de la lista de entrenamientos
    val expandir = MutableLiveData<Boolean>()

    //Variable para coger los datos de firebase
    val entrenamientos = MutableLiveData<List<ItemEntrenamiento>>()

    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }
    init {
        getEntrenamientos()
    }

    fun getEntrenamientos() {
        viewModelScope.launch {
            try {
                val db = Firebase.firestore
                val result = db.collection("entrenamientos").get().await()
                val entrenamientosList = result.map { document ->
                    ItemEntrenamiento(
                        document.getString("Fecha Entrenamiento") ?: "",
                        document.getString("Parte del cuerpo") ?: "",
                        document.getLong("Series")?.toString() ?: "",
                        document.getLong("Repeticiones")?.toString() ?: "",
                        document.getDouble("Peso")?.toString() ?: "",
                        document.getString("Ejercicios") ?: ""
                    )
                }
                entrenamientos.value = entrenamientosList
            } catch (exception: Exception) {
                Log.w(TAG, "Error getting documents.", exception)
            }
        }
    }

}