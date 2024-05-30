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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class SportViewModel : ViewModel() {

    //Variable para el DropdownMenu
    private val _expandir = MutableLiveData<Boolean>(false)
    val expandir: LiveData<Boolean> = _expandir

    //Variable para el alertDialog
    private val _mostrarDialog = MutableLiveData<Boolean>(false)
    val mostrarDialog: LiveData<Boolean> = _mostrarDialog

    fun setMostrarDialog(){
        _mostrarDialog.value = !(_mostrarDialog.value ?: false)
    }

    fun setDesplegable(){
        _expandir.value = !(_expandir.value ?: false)
    }
    fun cerrarSesion() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()

    }
    fun limpiarEntrenamientos() {
        _entrenamientos.value = emptyList()
        Log.d(TAG, "Entrenamientos limpiados")
    }
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

    private fun getEntrenamientos() {
        viewModelScope.launch {
            _cargaDatos.value = true
            try {
                val db = Firebase.firestore
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    val document = db.collection("entrenamientos").document(user.uid).get().await()
                    val entrenamiento = ItemEntrenamiento(
                        id = document.id,
                        fecha = document.getString("Fecha Entrenamiento") ?: "",
                        parteCuerpo = document.getString("Parte del cuerpo") ?: "",
                        series = document.getLong("Series")?.toString() ?: "",
                        repeticiones = document.getLong("Repeticiones")?.toString() ?: "",
                        pesoInicial = document.getDouble("Peso Inicial")?.toString() ?: "",
                        pesoFinal = document.getDouble("Peso Final")?.toString() ?: "",
                        ejercicios = document.getString("Ejercicios") ?: ""
                    )
                    _entrenamientos.value = listOf(entrenamiento)
                }
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


