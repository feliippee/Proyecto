package com.example.nutricionydeportefr.pantallas.registroDieta

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class RegistroDietaViewModel : ViewModel() {

    //Variable para el DropdownMenu
    private val _expandir = MutableLiveData<Boolean>(false)
    val expandir: LiveData<Boolean> = _expandir

    //Variable para modificar los campos
    private val _fechaDieta = MutableLiveData<String>()
    val fechaDieta: LiveData<String> = _fechaDieta

    private val _comidaseleccionada = MutableLiveData<String>()
    val comidaseleccionada: LiveData<String> = _comidaseleccionada

    private val _menu = MutableLiveData<String>()
    val menu: LiveData<String> = _menu

    private val _calorias = MutableLiveData<String>()
    val calorias: LiveData<String> = _calorias

    private val _cantidad = MutableLiveData<String>()
    val cantidad: LiveData<String> = _cantidad

    private val _suplementacion = MutableLiveData<String>()
    val suplementacion: LiveData<String> = _suplementacion

    //Variables para mostrar errores en los campos
    private val _fechaError = MutableLiveData<String?>()
    val fechaError: LiveData<String?> = _fechaError

    private val _comidaSeleccionadaError = MutableLiveData<String?>()
    val comidaSeleccionadaError: LiveData<String?> = _comidaSeleccionadaError

    private val _menuError = MutableLiveData<String?>()
    val menuError: LiveData<String?> = _menuError

    private val _caloriasError = MutableLiveData<String?>()
    val caloriasError: LiveData<String?> = _caloriasError

    private val _cantidadError = MutableLiveData<String?>()
    val cantidadError: LiveData<String?> = _cantidadError

    private val _suplementacionError = MutableLiveData<String?>()
    val suplementacionError: LiveData<String?> = _suplementacionError

    //Funciones para modificar los campos
    fun onfechaDietaChanged(fechaDieta: String) {
        _fechaDieta.value = fechaDieta
    }

    fun onComidaChange(comidaseleccionada: String) {
        _comidaseleccionada.value = comidaseleccionada
    }

    fun onMenuChange(cantidadPlatos: String) {
        _menu.value = cantidadPlatos
    }

    fun onCaloriasChange(calorias: String) {
        _calorias.value = calorias
    }

    fun onCantidadChange(cantidad: String) {
        _cantidad.value = cantidad
    }

    fun onSuplementacionChange(suplementacion: String) {
        _suplementacion.value = suplementacion
    }

    fun setDesplegable() {
        _expandir.value = !(_expandir.value ?: false)
    }

    init {
        fechaDieta.observeForever {
            _fechaError.value = null
        }
        comidaseleccionada.observeForever {
            _comidaSeleccionadaError.value = null
        }
        menu.observeForever {
            _menuError.value = null
        }
        calorias.observeForever {
            _caloriasError.value = null
        }
    }

    //Funcion DaterPickerDialog
    fun FechaDialog(context: Context, calendar: Calendar, onDateSelected: (String) -> Unit) {
        val fecha = DatePickerDialog(
            context,
            { view: DatePicker, year: Int, month: Int, day: Int ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                formatoFecha(calendar, onDateSelected)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        fecha.datePicker.maxDate = System.currentTimeMillis()
        fecha.show()
    }

    //Funcion para formato de la fecha
    fun formatoFecha(calendar: Calendar, onDateSelected: (String) -> Unit) {
        val fechaformato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fecha = fechaformato.format(calendar.time)
        onDateSelected(fecha)
    }



    @OptIn(DelicateCoroutinesApi::class)
    fun comprobarCamposDieta(
        fechaDieta: String,
        comidaseleccionada: String,
        menu: String,
        calorias: String,
        cantidad: String,
        suplementacion: String,
        navController: NavController
    ) {

        val caloriasNumber = calorias.toIntOrNull()

        if (fechaDieta.isEmpty()) {
            _fechaError.value = "Fecha no puede estar vacio"
            Log.d("Registro Entreno", "Campo Fecha Vacio")
        } else if (comidaseleccionada.isEmpty()) {
            _comidaSeleccionadaError.value = "Comida no puede estar vacio"
            Log.d("Registro Entreno", "Campo Comida Vacio")
        } else if(menu.isEmpty()){
            _menuError.value = "Menu no puede estar vacio"
            Log.d("Registro Entreno", "Campo Menu Vacio")
        } else if (caloriasNumber == null || caloriasNumber <= 0) {
            _caloriasError.value = "Calorias no puede estar vacio"
            Log.d("Registro Entreno", "Campo Calorias Vacio")
        } else {
            _calorias.value = caloriasNumber.toString()
            GlobalScope.launch(Dispatchers.Main) {
                navController.navigate("progressBar")
                registrarDatosAlimentacion(
                    comidaseleccionada,
                    menu,
                    caloriasNumber,
                    cantidad,
                    suplementacion,
                    fechaDieta
                )
                Log.d("Registro Alimentacion", "Datos Guardados en Firebase")
                delay(2000)
                navController.navigate("alimentacion")
            }

        }
    }


    //Funcion para guardar los datos de la alimentacion en Firebase
    fun registrarDatosAlimentacion(
        comidaseleccionada: String,
        menu: String,
        calorias: Int,
        cantidad: String,
        suplementacion: String,
        fechaDieta: String
    ) {
        val db = Firebase.firestore
        val usuario = hashMapOf(
            "Comida Seleccionada" to comidaseleccionada,
            "Menu" to menu,
            "Calorias" to calorias,
            "Cantidad" to cantidad,
            "Suplementacion" to suplementacion,
            "Fecha Alimentacion" to fechaDieta
        )
        db.collection("alimentacion")
            .add(usuario)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Error adding document $e")
            }
    }
}