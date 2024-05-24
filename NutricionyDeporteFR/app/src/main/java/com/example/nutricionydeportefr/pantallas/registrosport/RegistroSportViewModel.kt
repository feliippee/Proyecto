package com.example.nutricionydeportefr.pantallas.registrosport

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class RegistroSportViewModel: ViewModel() {
    //Variable para modificar los campos
    private val _fechaEntrenamiento = MutableLiveData<String>()
    val fechaEntrenamiento: LiveData<String> = _fechaEntrenamiento

    private val _parteCuerpo = MutableLiveData<String>()
    val parteCuerpo: LiveData<String> = _parteCuerpo

    private val _ejercicios = MutableLiveData<String>()
    val ejercicios: LiveData<String> = _ejercicios

    private val _series = MutableLiveData<String>()
    val series: LiveData<String> = _series

    private val _repeticiones = MutableLiveData<String>()
    val repeticiones: LiveData<String> = _repeticiones

    private val _pesoInicial = MutableLiveData<String>()
    val pesoInicial: LiveData<String> = _pesoInicial

    private val _pesoFinal = MutableLiveData<String>()
    val pesoFinal: LiveData<String> = _pesoFinal

    //Variables para mostrar errores en los campos
    private val _fechaError = MutableLiveData<String?>()
    val fechaError: LiveData<String?> = _fechaError

    private val _parteCuerpoError = MutableLiveData<String?>()
    val parteCuerpoError: LiveData<String?> = _parteCuerpoError

    private val _ejerciciosError = MutableLiveData<String?>()
    val ejerciciosError: LiveData<String?> = _ejerciciosError

    private val _seriesError = MutableLiveData<String?>()
    val seriesError: LiveData<String?> = _seriesError

    private val _repeticionesError = MutableLiveData<String?>()
    val repeticionesError: LiveData<String?> = _repeticionesError

    private val _pesoInicialError = MutableLiveData<String?>()
    val pesoInicialError: LiveData<String?> = _pesoInicialError

    private val _pesoFinalError = MutableLiveData<String?>()
    val pesoFinalError: LiveData<String?> = _pesoFinalError
    fun onfechaEntrenamientoChanged(fechaNacimiento: String) {
        _fechaEntrenamiento.value = fechaNacimiento
    }
    fun onparteCuerpoChanged(ejercicios: String) {
        _parteCuerpo.value = ejercicios
    }
    fun onEjerciciosChanged(ejercicios: String) {
        _ejercicios.value = ejercicios
    }
    fun onSeriesChanged(series: String) {
       _series.value = series
    }
    fun onRepeticionesChanged(repeticiones: String) {

       _repeticiones.value = repeticiones
    }
    fun onPesoInicialChanged(pesoinicial: String) {
        _pesoInicial.value = pesoinicial
    }
    fun onPesoFinalChanged(pesofinal: String) {
        _pesoFinal.value = pesofinal
    }
    init {

        fechaEntrenamiento.observeForever {
            _fechaError.value = null
        }
        parteCuerpo.observeForever {
            _parteCuerpoError.value = null
        }
        ejercicios.observeForever {
            _ejerciciosError.value = null
        }
        series.observeForever {
            _seriesError.value = null
        }
        repeticiones.observeForever {
            _repeticionesError.value = null
        }
        pesoInicial.observeForever {
            _pesoInicialError.value = null
        }
        pesoFinal.observeForever {
            _pesoFinalError.value = null
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
    fun compobarCamposEntreno(
        fechaEntrenamiento: String,
        partecuerpo: String,
        ejercicios: String,
        series: String,
        repeticiones: String,
        pesoInicial: String,
        pesoFinal: String,
        context: Context,
        navController: NavController

    ) {

        val seriesNumber = series.toIntOrNull()
        val repeticionesNumber = repeticiones.toIntOrNull()
        val pesoInicialNumber = pesoInicial.toFloatOrNull()
        val pesoFinalNumber = pesoFinal.toFloatOrNull()
        if (fechaEntrenamiento.isEmpty()) {
            _fechaError.value = "Fecha de nacimiento no puede estar vacio"
            Log.d("Registro Entreno", "Campo Fecha Vacio")
        } else if (partecuerpo.isEmpty()) {
            _parteCuerpoError.value = "Campo Obligatorio"
            Log.d("Registro Entreno", "Campo Parte del Cuerpo Vacio")
        } else if (ejercicios.isEmpty()) {
            _ejerciciosError.value = "Campo Obligatorio"
            Log.d("Registro Entreno", "Campo Ejercicios Vacio")
        } else if (seriesNumber == null || seriesNumber <= 0) {
            _seriesError.value = "Las series deben ser un número mayor que 0"
            Log.d("Registro Entreno", "Campo Series Invalido")
        } else if (repeticionesNumber == null || repeticionesNumber <= 0) {
            _repeticionesError.value = "Las repeticiones deben ser un número mayor que 0"
            Log.d("Registro Entreno", "Campo Repeticiones Invalido")
        } else if (pesoInicialNumber == null || pesoInicialNumber <= 0f) {
            _pesoInicialError.value = "El peso debe ser un número mayor que 0"
            Log.d("Registro Entreno", "Campo Peso Inicial Invalido")
        } else if (pesoFinalNumber == null || pesoFinalNumber <= 0f) {
            _pesoFinalError.value = "El peso debe ser un número mayor que 0"
            Log.d("Registro Entreno", "Campo Peso Final Invalido")
        } else {
            _series.value = seriesNumber.toString()
            _repeticiones.value = repeticionesNumber.toString()
            _pesoInicial.value = pesoInicialNumber.toString()
            _pesoFinal.value = pesoFinalNumber.toString()
            GlobalScope.launch(Dispatchers.Main) {
                navController.navigate("progressBar")
                registrarDatosEntrenamientos(partecuerpo, ejercicios, seriesNumber, repeticionesNumber, pesoInicialNumber, pesoFinalNumber, fechaEntrenamiento)
                delay(2000)  // Espera un segundo y medio
                Toast.makeText(context, "Entrenamiento registrado correctamente", Toast.LENGTH_SHORT).show()
                Log.d("Registro Entreno", "Entrenamiento registrado correctamente")
                navController.navigate("ejercicios")
            }

        }
    }

    //Funcion para guardar los datos de los usuarios en Firebase
    fun registrarDatosEntrenamientos(
        partecuerpo: String,
        ejercicios: String,
        series: Int,
        repeticiones: Int,
        pesoInicial: Float,
        pesoFinal: Float,
        fechaEntrenamiento: String
    ) {
        val db = Firebase.firestore
        val usuario = hashMapOf(
            "Parte del cuerpo" to partecuerpo,
            "Ejercicios" to ejercicios,
            "Series" to series,
            "Repeticiones" to repeticiones,
            "Peso Inicial" to pesoInicial,
            "Peso Final" to pesoFinal,
            "Fecha Entrenamiento" to fechaEntrenamiento
        )
        db.collection("entrenamientos")
            .add(usuario)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Error adding document $e")
            }
    }
}