package com.example.nutricionydeportefr.pantallas.sport

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutricionydeportefr.itemsRecycler.ItemEntrenamiento
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
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

    private val _cargaDatos = MutableLiveData(true)
    val cargaDatos: LiveData<Boolean> = _cargaDatos

    init {
        getEntrenamientos()
    }

    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }

    private  fun getEntrenamientos() {
        viewModelScope.launch {
            _cargaDatos.value = true
            try {
                val db = Firebase.firestore
                val usuarioId = FirebaseAuth.getInstance().currentUser?.uid
                val result = db.collection("entrenamientos")
                    .whereEqualTo("usuarioId", usuarioId).get().await()
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
                Log.w("SportViewModel", "Error al obtener los datos.", exception)
            } finally {
                _cargaDatos.value = false

            }
        }
    }

    fun deleteEntrenamiento(itemEntrenamiento: ItemEntrenamiento) {
        viewModelScope.launch {
            _cargaDatos.value = true
            try {
                val db = Firebase.firestore
                db.collection("entrenamientos").document(itemEntrenamiento.id).delete().await()
                getEntrenamientos() // Refresh the list after deletion
            } catch (exception: Exception) {
                Log.w("SportViewModel", "Error al borrar el documento", exception)
            } finally {
                _cargaDatos.value = false
            }
        }
    }

}


