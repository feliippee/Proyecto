package com.example.nutricionydeportefr.pantallas.alimentacion

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutricionydeportefr.itemsRecycler.ItemAlimentacion
import com.example.nutricionydeportefr.itemsRecycler.ItemEntrenamiento
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AlimentacionViewModel : ViewModel() {

    //Variable para la seleccion de las opciones del bottom menu
    private var _opcionBottonMenu = MutableLiveData(2)
    var opcionBottonMenu: LiveData<Int> = _opcionBottonMenu

    private val _alimentacion = MutableLiveData<List<ItemAlimentacion>>()
    val alimentacion: LiveData<List<ItemAlimentacion>> = _alimentacion

    private val _cargaDatos = MutableLiveData(true)
    val cargaDatos: LiveData<Boolean> = _cargaDatos

    init {
        getAlimentacion()
    }

    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }

    private fun getAlimentacion() {
        viewModelScope.launch {
            _cargaDatos.value = true
            try {
                val db = Firebase.firestore
                val result = db.collection("alimentacion").get().await()
                val alimentacionList = result.map { document ->
                    ItemAlimentacion(
                        id = document.id,
                        fecha = document.getString("Fecha Alimentacion") ?: "",
                        comida = document.getString("Comida Seleccionada") ?: "",
                        menu = document.getString("Menu") ?: "",
                        cantidad = document.getString("Cantidad") ?: "",
                        calorias = document.getLong("Calorias")?.toString() ?: "",
                        suplementacion = document.getString("Suplementacion") ?: "",
                    )
                }
                _alimentacion.value = alimentacionList
            } catch (exception: Exception) {
                Log.w("AlimentacionViewModel", "Error al obtener los datos.", exception)
            } finally {
                _cargaDatos.value = false

            }
        }
    }

    fun borrarAlimentacion(itemAlimentacion: ItemAlimentacion) {
        viewModelScope.launch {
            _cargaDatos.value = true
            try {
                val db = Firebase.firestore
                db.collection("alimentacion").document(itemAlimentacion.id).delete().await()
                getAlimentacion() // Refresh the list after deletion
            } catch (exception: Exception) {
                Log.w("AlimentacionViewModel", "Error al borrar el documento", exception)
            } finally {
                _cargaDatos.value = false
            }
        }
    }
}