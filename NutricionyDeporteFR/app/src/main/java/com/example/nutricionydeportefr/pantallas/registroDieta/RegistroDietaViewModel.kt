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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

lateinit var firebaseAuth: FirebaseAuth

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

    private val _racionVerduras = MutableLiveData<String>()
    val racionVerduras: LiveData<String> = _racionVerduras

    private val _racionLacteo = MutableLiveData<String>()
    val racionLacteo: LiveData<String> = _racionLacteo

    private val _racionProteina = MutableLiveData<String>()
    val racionProteina: LiveData<String> = _racionProteina

    private val _racionFruta = MutableLiveData<String>()
    val racionFruta: LiveData<String> = _racionFruta

    private val _racionHidratos = MutableLiveData<String>()
    val racionHidratos: LiveData<String> = _racionHidratos

    private val _racionGrasas = MutableLiveData<String>()
    val racionGrasas: LiveData<String> = _racionGrasas

    private val _suplementacion = MutableLiveData<String>()
    val suplementacion: LiveData<String> = _suplementacion


    //Variables para mostrar errores en los campos
    private val _fechaError = MutableLiveData<String?>()
    val fechaError: LiveData<String?> = _fechaError

    private val _comidaSeleccionadaError = MutableLiveData<String?>()
    val comidaSeleccionadaError: LiveData<String?> = _comidaSeleccionadaError

    private val _menuError = MutableLiveData<String?>()
    val menuError: LiveData<String?> = _menuError

    private val _racionLacteoError = MutableLiveData<String?>()
    val racionLacteoError: LiveData<String?> = _racionLacteoError

    private val _racionFrutaError = MutableLiveData<String?>()
    val racionFrutaError: LiveData<String?> = _racionFrutaError

    private val _racionHidratosError = MutableLiveData<String?>()
    val racionHidratosError: LiveData<String?> = _racionHidratosError

    private val _racionGrasasError = MutableLiveData<String?>()
    val racionGrasasError: LiveData<String?> = _racionGrasasError

    private val _racionProteinaError = MutableLiveData<String?>()
    val racionProteinaError: LiveData<String?> = _racionProteinaError

    private val _racionVerduraError = MutableLiveData<String?>()
    val racionVerduraError: LiveData<String?> = _racionVerduraError

    private val _suplementacionError = MutableLiveData<String?>()
    val suplementacionError: LiveData<String?> = _suplementacionError

    init {
        Firebase.firestore
        firebaseAuth = FirebaseAuth.getInstance()

        fechaDieta.observeForever {
            _fechaError.value = null
        }
        comidaseleccionada.observeForever {
            _comidaSeleccionadaError.value = null
        }
        menu.observeForever {
            _menuError.value = null
        }
        racionVerduras.observeForever {
            _racionLacteoError.value = null
        }
        racionLacteo.observeForever {
            _racionLacteoError.value = null
        }
        racionFruta.observeForever {
            _racionFrutaError.value = null
        }
        racionHidratos.observeForever {
            _racionHidratosError.value = null
        }
        racionGrasas.observeForever {
            _racionGrasasError.value = null
        }
        racionProteina.observeForever {
            _racionProteinaError.value = null
        }
        suplementacion.observeForever {
            _suplementacionError.value = null
        }
    }

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

    fun onVerduraChange(racionVerdura: String) {
        _racionVerduras.value = racionVerdura
    }

    fun onLacteoChange(racionLacteo: String) {
        _racionLacteo.value = racionLacteo
    }

    fun onFrutaChange(racionFruta: String) {
        _racionFruta.value = racionFruta
    }

    fun onHidratosChange(racionHidratos: String) {
        _racionHidratos.value = racionHidratos
    }

    fun onGrasasChange(racionGrasas: String) {
        _racionGrasas.value = racionGrasas
    }

    fun onProteinaChange(racionProteina: String) {
        _racionProteina.value = racionProteina
    }

    fun onSuplementacionChange(suplementacion: String) {
        _suplementacion.value = suplementacion
    }

    fun setDesplegable() {
        _expandir.value = !(_expandir.value ?: false)
    }

    //Funcion DaterPickerDialog
    fun fechaDialog(context: Context, calendar: Calendar, onDateSelected: (String) -> Unit) {
        val fecha = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
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
    private fun formatoFecha(calendar: Calendar, onDateSelected: (String) -> Unit) {
        val fechaformato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fecha = fechaformato.format(calendar.time)
        onDateSelected(fecha)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun comprobarCamposDieta(
        fechaDieta: String,
        comidaseleccionada: String,
        menu: String,
        racionLacteo: String,
        racionVerduras: String,
        racionFruta: String,
        racionHidratos: String,
        racionGrasas: String,
        racionProteina: String,
        suplementacion: String,
        navController: NavController
    ) {

        val lacteoNumber = racionLacteo.toFloatOrNull()
        val verdurasNumber = racionVerduras.toFloatOrNull()
        val frutaNumber = racionFruta.toFloatOrNull()
        val hidratosNumber = racionHidratos.toFloatOrNull()
        val grasasNumber = racionGrasas.toFloatOrNull()
        val proteinaNumber = racionProteina.toFloatOrNull()

        if (fechaDieta.isEmpty()) {
            _fechaError.value = "Fecha no puede estar vacio"
            Log.d("Registro Entreno", "Campo Fecha Vacio")
        } else if (comidaseleccionada.isEmpty()) {
            _comidaSeleccionadaError.value = "Comida no puede estar vacio"
            Log.d("Registro Entreno", "Campo Comida Vacio")
        } else if (menu.isEmpty()) {
            _menuError.value = "Menu no puede estar vacio"
            Log.d("Registro Entreno", "Campo Menu Vacio")
        } else if (lacteoNumber == null || lacteoNumber < 0) {
            _racionLacteoError.value = "La racion de lacteo no puede estar vacio o ser positivo"
            Log.d("Registro Entreno", "Lacteo Calorias Vacio")
        } else if (verdurasNumber == null || verdurasNumber < 0) {
            _racionVerduraError.value = "La racion de verduras no puede estar vacio o ser positivo"
            Log.d("Registro Entreno", "Verduras Calorias Vacio")
        } else if (frutaNumber == null || frutaNumber < 0) {
            _racionFrutaError.value = "La racion de fruta no puede estar vacio o ser positivo"
            Log.d("Registro Entreno", "Fruta Calorias Vacio")
        } else if (hidratosNumber == null || hidratosNumber < 0) {
            _racionHidratosError.value = "La racion de hidratos no puede estar vacio o ser positivo"
            Log.d("Registro Entreno", "Hidratos Calorias Vacio")
        } else if (grasasNumber == null || grasasNumber < 0) {
            _racionGrasasError.value = "La racion de grasas no puede estar vacio o ser positivo"
            Log.d("Registro Entreno", "Grasas Calorias Vacio")
        } else if (proteinaNumber == null || proteinaNumber < 0) {
            _racionProteinaError.value = "La racion de proteina no puede estar vacio o ser positivo"
            Log.d("Registro Entreno", "Proteina Calorias Vacio")
        } else if (suplementacion.isEmpty()) {
            _suplementacionError.value = "Suplementacion no puede estar vacio"
            Log.d("Registro Entreno", "Suplementacion Vacio")
        } else {
            _racionVerduras.value = verdurasNumber.toString()
            _racionLacteo.value = lacteoNumber.toString()
            _racionFruta.value = frutaNumber.toString()
            _racionHidratos.value = hidratosNumber.toString()
            _racionGrasas.value = grasasNumber.toString()
            _racionProteina.value = proteinaNumber.toString()

            GlobalScope.launch(Dispatchers.Main) {
                navController.navigate("progressBar")
                registrarDatosAlimentacion(
                    comidaseleccionada,
                    menu,
                    verdurasNumber,
                    lacteoNumber,
                    frutaNumber,
                    hidratosNumber,
                    grasasNumber,
                    proteinaNumber,
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
    private fun registrarDatosAlimentacion(
        comidaseleccionada: String,
        menu: String,
        verdurasNumber: Float,
        lacteoNumber: Float,
        frutaNumber: Float,
        hidratosNumber: Float,
        grasasNumber: Float,
        proteinaNumber: Float,
        suplementacion: String,
        fechaDieta: String
    ) {
        val user = firebaseAuth.currentUser

        if (user != null) {

            //Convertimos la fecha a timpo timestamp para guardarlo en firebase
            val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val fecha = formatoFecha.parse(fechaDieta)

            val db = Firebase.firestore
            val usuarioid = user.uid
            val dietaDatos = hashMapOf(
                "usuarioId" to usuarioid,
                "Comida Seleccionada" to comidaseleccionada,
                "Menu" to menu,
                "Racion Verduras" to verdurasNumber,
                "Racion Lacteo" to lacteoNumber,
                "Racion Fruta" to frutaNumber,
                "Racion Hidratos" to hidratosNumber,
                "Racion Grasas" to grasasNumber,
                "Racion Proteina" to proteinaNumber,
                "Suplementacion" to suplementacion,
                "Fecha Alimentacion" to fecha
            )
            db.collection("alimentacion")
                .add(dietaDatos)
                .addOnSuccessListener { documentReference ->
                    Log.d("Registro Alimentacion", "Documento Creado con ID: ${documentReference.id}")

                }
                .addOnFailureListener { e ->
                    Log.d("Registro Alimentacion", "Error al guardar los datos en Firebase", e)
                }
        }
    }
}