package com.example.nutricionydeportefr.pantallas.registro

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.*
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

val calendar = Calendar.getInstance()

//Variables de Firebase
lateinit var firebaseAuth: FirebaseAuth

class RegistroViewModel : ViewModel() {

    //Variable para modificar los campos

    private val _usuario = MutableLiveData<String>()
    val usuario: LiveData<String> = _usuario

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _mostrarpassword = MutableLiveData<Boolean>(false)
    val mostrarpassword: LiveData<Boolean> = _mostrarpassword

    private val _confirmarPassword = MutableLiveData<String>()
    val confirmarPassword: LiveData<String> = _confirmarPassword

    private val _correo = MutableLiveData<String>()
    val correo: LiveData<String> = _correo

    private val _fechaNacimiento = MutableLiveData<String>()
    val fechaNacimiento: LiveData<String> = _fechaNacimiento

    //Variables para mostrar errores en los campos

    private val _usuarioError = MutableLiveData<String?>()
    val usuarioError: LiveData<String?> = _usuarioError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    private val _fechaError = MutableLiveData<String?>()
    val fechaError: LiveData<String?> = _fechaError

    private val _confirmarPasswordError = MutableLiveData<String?>()
    val confirmarPasswordError: LiveData<String?> = _confirmarPasswordError

    //Funciones para modificar campos
    fun onUsuarioChanged(usuario: String) {
        _usuario.value = usuario
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
    }

    fun onMostrarPasswod() {
        _mostrarpassword.value = _mostrarpassword.value?.not()
    }

    fun onConfirmarPasswordChanged(confirmarPassword: String) {
        _confirmarPassword.value = confirmarPassword
    }

    fun onCorreoChanged(correo: String) {
        _correo.value = correo
    }

    fun onFechaNacimientoChanged(fechaNacimiento: String) {
        _fechaNacimiento.value = fechaNacimiento
    }


    //En caso de que escriba en el campo, se quita el erro del campo
    init {
        usuario.observeForever {
            _usuarioError.value = null
        }

        password.observeForever {
            _passwordError.value = null
        }
        confirmarPassword.observeForever {
            _confirmarPasswordError.value = null
        }

        correo.observeForever {
            _emailError.value = null
        }
        fechaNacimiento.observeForever {
            _fechaError.value = null
        }
    }

    fun compobarCampos(
        usuario: String,
        password: String,
        confirmarPassword: String,
        correo: String,
        fechaNacimiento: String,
        context: Context,
        navController: NavController,
        ) {
        if (usuario.isEmpty()) {
            _usuarioError.value = "Usuario no puede estar vacio"
        } else if (password.isEmpty()) {
            _passwordError.value = "Contraseña no puede estar vacio"
        } else if (password.length < 8) {
            _passwordError.value = "La contraseña debe de tener\n al menos 8 caracteres"
        } else if (confirmarPassword.isEmpty() || confirmarPassword != password) {
            _confirmarPasswordError.value = "Las contraseñas no coinciden"
        } else if (correo.isEmpty()) {
            _emailError.value = "Correo no puede estar vacio"
        } else if (fechaNacimiento.isEmpty()) {
            _fechaError.value = "Fecha de nacimiento no puede estar vacio"
        } else if (!validarCorreo(correo)) {
            _emailError.value = "El correo introducido no es válido"
        } else {
            registrarUsuario(usuario, password, correo, fechaNacimiento, context, navController)
        }
    }

    //Funcion para registrar usuario en firebase
    @OptIn(DelicateCoroutinesApi::class)
    private fun registrarUsuario(
        usuario: String,
        contrasena: String,
        correo: String,
        fechaNacimiento: String,
        //Context es para indicarle donde queremos mostrar el mesaje
        context: android.content.Context,
        navController: NavController
    ) {
        firebaseAuth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //Obtenemos el usuario logueado en firebase
                    val user = firebaseAuth.currentUser
                    //Actualizamos el perfil del usuario y le ponemos el nombre
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(usuario)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                registrarDatosUsuarios(usuario, correo, fechaNacimiento)
                                val sharedPref = context.getSharedPreferences("Nombre Usuario", Context.MODE_PRIVATE)
                                with (sharedPref.edit()) {
                                    putString("email", correo)
                                    putString("password", contrasena)
                                    apply()
                                }
                                GlobalScope.launch(Dispatchers.Main) {
                                    Toast.makeText(context, "Usuario registrado con exito", Toast.LENGTH_SHORT).show()
                                    delay(2000)
                                    navController.navigate("home")
                                }

                            }
                        }
                } else {
                    val exception = task.exception
                    when (exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            //Contraseña invalida
                            Toast.makeText(
                                context,
                                "La contraseña  debe de contener al menos 8 caracteres",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is FirebaseAuthInvalidUserException -> {
                            //Email invalido
                            Toast.makeText(context, "El correo introducido no es válido", Toast.LENGTH_SHORT).show()
                        }

                        is FirebaseAuthUserCollisionException -> {
                            //Email en uso
                            Toast.makeText(context, "El correo introducido ya está en uso", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            //Otros errores
                            Toast.makeText(context, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
    }

    //Funcion para guardar los datos de los usuarios en Firebase
    fun registrarDatosUsuarios(
        usuario: String,
        correo: String,
        fechaNacimiento: String
    ) {
        val db = Firebase.firestore
        val usuario = hashMapOf(
            "nombre" to usuario,
            "correo" to correo,
            "fecha" to fechaNacimiento
        )
        db.collection("usuarios")
            .add(usuario)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Error adding document $e")
            }
    }

    //Funcion para validar el correo
    fun validarCorreo(correo: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
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