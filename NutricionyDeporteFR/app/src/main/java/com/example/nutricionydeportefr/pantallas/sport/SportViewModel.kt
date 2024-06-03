package com.example.nutricionydeportefr.pantallas.sport

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutricionydeportefr.itemsRecycler.ItemEntrenamiento
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*


class SportViewModel private constructor() : ViewModel() {


    // Variable para coger los datos de Firebase
    private val _entrenamientos = MutableLiveData<List<ItemEntrenamiento>>()
    val entrenamientos: LiveData<List<ItemEntrenamiento>> = _entrenamientos

    private val _cargaDatos = MutableLiveData(true)
    val cargaDatos: LiveData<Boolean> = _cargaDatos

    // Inicializamos la carga de datos
    init {
        getEntrenamientos()
    }

    // Funcion para obtener los datos de Firebase
    fun getEntrenamientos() {
        viewModelScope.launch {
            _cargaDatos.value = true
            try {
                val db = Firebase.firestore
                val usuarioId = FirebaseAuth.getInstance().currentUser?.uid
                val result = db.collection("entrenamientos")
                    .whereEqualTo("usuarioId", usuarioId)
                    .orderBy("Fecha Entrenamiento", Query.Direction.DESCENDING)
                    .get().await()
                val entrenamientosList = result.map { document ->

                    val timestamp = document.getTimestamp("Fecha Entrenamiento")
                    val date = timestamp?.toDate()
                    val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val fecha = date?.let { formatoFecha.format(it) } ?: ""

                    ItemEntrenamiento(
                        id = document.id,
                        fecha = fecha,
                        parteCuerpo = document.getString("Parte del cuerpo") ?: "",
                        series = document.getLong("Series")?.toString() ?: "",
                        repeticiones = document.getLong("Repeticiones")?.toString() ?: "",
                        pesoInicial = document.getDouble("Peso Inicial")?.toString() ?: "",
                        pesoFinal = document.getDouble("Peso Final")?.toString() ?: "",
                        ejercicios = document.getString("Ejercicios") ?: ""
                    )

                }
                _entrenamientos.value = entrenamientosList
                Log.d("SportViewModel", "Datos obtenidos correctamente.")
            } catch (exception: Exception) {
                Log.d("SportViewModel", "Error al obtener los datos.", exception)
            } finally {
                _cargaDatos.value = false

            }
        }
    }

    //Funcion para borrar entrenamiento
    fun deleteEntrenamiento(itemEntrenamiento: ItemEntrenamiento) {
        viewModelScope.launch {
            _cargaDatos.value = true
            try {
                val db = Firebase.firestore
                db.collection("entrenamientos").document(itemEntrenamiento.id).delete().await()
                getEntrenamientos() // Refresh the list after deletion
            } catch (exception: Exception) {
                Log.d("SportViewModel", "Error al borrar el documento", exception)
            } finally {
                _cargaDatos.value = false
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SportViewModel? = null

        fun getInstance(): SportViewModel {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = SportViewModel()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}


