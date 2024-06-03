package com.example.nutricionydeportefr.pantallas.alimentacion

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutricionydeportefr.itemsRecycler.ItemAlimentacion
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*


class AlimentacionViewModel private constructor() : ViewModel() {

    //Variable LiveData para la optencion de los datos
    private val _alimentacion = MutableLiveData<List<ItemAlimentacion>>()
    val alimentacion: LiveData<List<ItemAlimentacion>> = _alimentacion

    private val _cargaDatos = MutableLiveData(true)
    val cargaDatos: LiveData<Boolean> = _cargaDatos

    //Inicializacion de las funciones
    init {
        getAlimentacion()
    }

    //Funcion para obtener los datos de alimentacion de firebase
    fun getAlimentacion() {
        viewModelScope.launch {
            _cargaDatos.value = true
            try {
                val db = Firebase.firestore
                val usuarioId = FirebaseAuth.getInstance().currentUser?.uid
                val result = db.collection("alimentacion")
                    .whereEqualTo("usuarioId", usuarioId)
                    .orderBy("Fecha Alimentacion", Query.Direction.DESCENDING)
                    .get().await()
                val alimentacionList = result.map { document ->

                    val timestamp = document.getTimestamp("Fecha Alimentacion")
                    val date = timestamp?.toDate()
                    val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val fecha = date?.let { formatoFecha.format(it) } ?: ""

                    ItemAlimentacion(
                        id = document.id,
                        fecha = fecha,
                        comida = document.getString("Comida Seleccionada") ?: "",
                        menu = document.getString("Menu") ?: "",
                        verduras = document.getDouble("Racion Verduras")?.toString() ?: "",
                        lacteos = document.getDouble("Racion Lacteo")?.toString() ?: "",
                        frutas = document.getDouble("Racion Fruta")?.toString() ?: "",
                        hidratos = document.getDouble("Racion Hidratos")?.toString() ?: "",
                        grasas = document.getDouble("Racion Grasas")?.toString() ?: "",
                        proteinas = document.getDouble("Racion Proteina")?.toString() ?: "",
                        suplementacion = document.getString("Suplementacion") ?: "",
                    )
                }
                _alimentacion.value = alimentacionList

            } catch (exception: Exception) {
                Log.d("AlimentacionViewModel", "Error al obtener los datos.", exception)
            } finally {
                _cargaDatos.value = false

            }
        }
    }

    //Funcion para borrar registro de alimentacion
    fun borrarAlimentacion(itemAlimentacion: ItemAlimentacion) {
        viewModelScope.launch {
            _cargaDatos.value = true
            try {
                val db = Firebase.firestore
                db.collection("alimentacion").document(itemAlimentacion.id).delete().await()
                getAlimentacion() //Mostramos la lista sin el elemento borrado
            } catch (exception: Exception) {
                Log.d("AlimentacionViewModel", "Error al borrar el documento", exception)
            } finally {
                _cargaDatos.value = false
            }
        }
    }

    //Singlenton para que persista los datos a pesar de cambiar de pantalla
    companion object {
        @Volatile
        private var INSTANCE: AlimentacionViewModel? = null

        fun getInstance(): AlimentacionViewModel {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = AlimentacionViewModel()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}