package com.example.nutricionydeportefr.pantallas.home


import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.Query
import java.util.*


class HomeViewModel private constructor() : ViewModel() {

    private val _entrenamientosDiarios = MutableLiveData(0)
    val entrenamientosDiarios: LiveData<Int> = _entrenamientosDiarios

    private val _alimentacionesDiarias = MutableLiveData(0)
    val alimentacionesDiarias: LiveData<Int> = _alimentacionesDiarias

    init {
        getEntrenamientosHoy()
        getAlimentacionesHoy()
    }

    //Funcion para obtener los entrenamientos de hoy
    fun  getEntrenamientosHoy() {
        viewModelScope.launch {
            val db = Firebase.firestore
            val usuarioId = FirebaseAuth.getInstance().currentUser?.uid
            val result = db.collection("entrenamientos")
                .whereEqualTo("usuarioId", usuarioId)
                .orderBy("Fecha Entrenamiento", Query.Direction.DESCENDING)
                .get().await()

            val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val fechaHoy = formatoFecha.format(Date())

            val entrenamientosHoy = result.documents.count { document ->
                val timestamp = document.getTimestamp("Fecha Entrenamiento")
                val fecha = timestamp?.toDate()?.let { formatoFecha.format(it) }
                fecha == fechaHoy
            }

            _entrenamientosDiarios.value = entrenamientosHoy
        }
    }
    //Funcion para obtener las alimentaciones registradas
    fun getAlimentacionesHoy() {
        viewModelScope.launch {
            val db = Firebase.firestore
            val usuarioId = FirebaseAuth.getInstance().currentUser?.uid
            val result = db.collection("alimentacion")
                .whereEqualTo("usuarioId", usuarioId)
                .orderBy("Fecha Alimentacion", Query.Direction.DESCENDING)
                .get().await()

            val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val fechaHoy = formatoFecha.format(Date())

            val alimentacionesHoy = result.documents.count { document ->
                val timestamp = document.getTimestamp("Fecha Alimentacion")
                val fecha = timestamp?.toDate()?.let { formatoFecha.format(it) }
                fecha == fechaHoy
            }

            _alimentacionesDiarias.value = alimentacionesHoy
        }
    }

    //Singlento para persistencia de datos entre view model
    companion object {
        @Volatile
        private var INSTANCE: HomeViewModel? = null

        fun getInstance(): HomeViewModel {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = HomeViewModel()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}