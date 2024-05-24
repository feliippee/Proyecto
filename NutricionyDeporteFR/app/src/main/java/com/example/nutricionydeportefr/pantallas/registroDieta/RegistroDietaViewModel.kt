package com.example.nutricionydeportefr.pantallas.registroDieta

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class RegistroDietaViewModel: ViewModel(){

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
    fun onComidaChange(comidaseleccionada:String) {
        _comidaseleccionada.value = comidaseleccionada
    }
    fun onMenuChange(cantidadPlatos:String) {
        _menu.value = cantidadPlatos
    }
    fun onCaloriasChange(calorias:String) {
        _calorias.value = calorias
    }
    fun onCantidadChange(cantidad:String) {
        _cantidad.value = cantidad
    }
    fun onSuplementacionChange(suplementacion:String) {
        _suplementacion.value = suplementacion
    }
    fun setDesplegable(){
        _expandir.value = !(_expandir.value ?: false)
    }

    init{
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
}