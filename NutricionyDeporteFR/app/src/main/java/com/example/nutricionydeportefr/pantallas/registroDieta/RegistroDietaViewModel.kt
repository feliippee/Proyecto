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

    init {
        Firebase.firestore
        firebaseAuth = FirebaseAuth.getInstance()
    }

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
        racionLacteo: String,
        racionVerduras: String,
        racionFruta: String,
        racionHidratos: String,
        racionGrasas: String,
        racionProteina: String,
        suplementacion: String,
        navController: NavController
    ) {

        val LacteoNumber = racionLacteo.toFloatOrNull()
        val VerdurasNumber = racionVerduras.toFloatOrNull()
        val FrutaNumber = racionFruta.toFloatOrNull()
        val HidratosNumber = racionHidratos.toFloatOrNull()
        val GrasasNumber = racionGrasas.toFloatOrNull()
        val ProteinaNumber = racionProteina.toFloatOrNull()

        if (fechaDieta.isEmpty()) {
            _fechaError.value = "Fecha no puede estar vacio"
            Log.d("Registro Entreno", "Campo Fecha Vacio")
        } else if (comidaseleccionada.isEmpty()) {
            _comidaSeleccionadaError.value = "Comida no puede estar vacio"
            Log.d("Registro Entreno", "Campo Comida Vacio")
        } else if (menu.isEmpty()) {
            _menuError.value = "Menu no puede estar vacio"
            Log.d("Registro Entreno", "Campo Menu Vacio")
        } else if (LacteoNumber == null || LacteoNumber < 0) {
            _racionLacteoError.value = "La racion de lacteo no puede estar vacio o ser positivo"
            Log.d("Registro Entreno", "Lacteo Calorias Vacio")
        } else if (VerdurasNumber == null || VerdurasNumber < 0) {
            _racionVerduraError.value = "La racion de verduras no puede estar vacio o ser positivo"
            Log.d("Registro Entreno", "Verduras Calorias Vacio")
        } else if (FrutaNumber == null || FrutaNumber < 0) {
            _racionFrutaError.value = "La racion de fruta no puede estar vacio o ser positivo"
            Log.d("Registro Entreno", "Fruta Calorias Vacio")
        } else if (HidratosNumber == null || HidratosNumber < 0) {
            _racionHidratosError.value = "La racion de hidratos no puede estar vacio o ser positivo"
            Log.d("Registro Entreno", "Hidratos Calorias Vacio")
        } else if (GrasasNumber == null || GrasasNumber < 0) {
            _racionGrasasError.value = "La racion de grasas no puede estar vacio o ser positivo"
            Log.d("Registro Entreno", "Grasas Calorias Vacio")
        } else if (ProteinaNumber == null || ProteinaNumber < 0) {
            _racionProteinaError.value = "La racion de proteina no puede estar vacio o ser positivo"
            Log.d("Registro Entreno", "Proteina Calorias Vacio")
        } else if (suplementacion.isEmpty()) {
            _suplementacionError.value = "Suplementacion no puede estar vacio"
            Log.d("Registro Entreno", "Suplementacion Vacio")
        } else {
            _racionVerduras.value = VerdurasNumber.toString()
            _racionLacteo.value = LacteoNumber.toString()
            _racionFruta.value = FrutaNumber.toString()
            _racionHidratos.value = HidratosNumber.toString()
            _racionGrasas.value = GrasasNumber.toString()
            _racionProteina.value = ProteinaNumber.toString()
            GlobalScope.launch(Dispatchers.Main) {
                navController.navigate("progressBar")
                registrarDatosAlimentacion(
                    comidaseleccionada,
                    menu,
                    VerdurasNumber,
                    LacteoNumber,
                    FrutaNumber,
                    HidratosNumber,
                    GrasasNumber,
                    ProteinaNumber,
                    suplementacion,
                    fechaDieta
                )
                Log.d("Registro Alimentacion", "Datos Guardados en Firebase")
                delay(2000)
                navController.navigate("alimentacion")
            }

        }
    }

    private val _totalRacionLacteo = MutableLiveData<Float>()
    val totalRacionLacteo: LiveData<Float> = _totalRacionLacteo
    fun sumarRaciones() {
        _totalRacionLacteo.value = (_totalRacionLacteo.value ?: 0f) + (racionLacteo.value?.toFloatOrNull() ?: 0f)
    Log.d("Registro Alimentacion", "Racion Lacteo Sumada $${totalRacionLacteo.value}")
    }

    //Funcion para guardar los datos de la alimentacion en Firebase
    fun registrarDatosAlimentacion(
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
                "Fecha Alimentacion" to fechaDieta
            )
            db.collection("alimentacion")
                .add(dietaDatos)
                .addOnSuccessListener { documentReference ->
                    println("DocumentSnapshot added with ID")
                    Log.d("Registro Alimentacion", "Funcion suma llamada")
                    sumarRaciones()
                    Log.d("Registro Alimentacion", "Valor racion lacteo: ${totalRacionLacteo.value}")
                }
                .addOnFailureListener { e ->
                    println("Error adding document $e")
                }

        }
    }

}