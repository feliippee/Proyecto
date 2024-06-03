package com.example.nutricionydeportefr.pantallas.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*

//Variable para la autenticacion de firebase
lateinit var firebaseAuth: FirebaseAuth

class LoginViewModel : ViewModel() {

    //Inicializamos la variable de autenticacion
    private val auth: FirebaseAuth = Firebase.auth

    //Variable livedata para la obtencion  y guardar datos
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _mostrarpassword = MutableLiveData(false)
    val mostrarpassword: LiveData<Boolean> = _mostrarpassword

    //Variables para mostrar errores en los campos
    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError


    //Funciones para obtener el valor de los campos y actualizarlos
    fun onEmailChanged(email: String) {
        _email.value = email
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
    }

    fun onMostrarPasswod() {
        _mostrarpassword.value = _mostrarpassword.value?.not()
    }

    //Funcion para iniciar sesion
    @OptIn(DelicateCoroutinesApi::class)
    private fun inicioSesion(
        email: String,
        password: String,
        context: android.content.Context,
        navController: NavController
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    GlobalScope.launch(Dispatchers.Main) {
                        //Mostramos un mensaje y llamamos al menu
                        Toast.makeText(context, "Inicio de sesion correcto", Toast.LENGTH_SHORT).show()
                        Log.d("Login", "Inicio de sesion correcto")
                        delay(1000)
                        //Bloqueo la pantalla para que no se pueda volver atras
                        navController.popBackStack()
                        navController.navigate("perfil")
                    }

                } else {
                    //Si no se ha podido iniciar sesion mostramos un mensaje
                    Toast.makeText(context, "Datos de acceso erroneos", Toast.LENGTH_SHORT).show()
                }
            }
    }
    //Funcion para validar el formato del correo
    private fun validarCorreo(correo: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }

    //En caso de que escriba en el campo, se quita el erro del campo
    init {
        email.observeForever {
            _emailError.value = null
        }
        password.observeForever {
            _passwordError.value = null
        }

    }

    //Funcion para comprobar los campos
    fun comprobarCampos(
        correo: String,
        password: String,
        context: android.content.Context,
        navController: NavController
    ) {
        //Comprobamos que los campos no esten vacios
        if (correo.isEmpty()) {
            _emailError.value = "Correo no puede estar vacio"
        } else if (!validarCorreo(correo)) {
            _emailError.value = "Correo no valido"
        } else if (password.isEmpty()) {
            _passwordError.value = "Contraseña no puede estar vacio"

        } else {
            //Comprobamos el inicio de sesion
            inicioSesion(correo, password, context, navController)
        }
    }

    //Funcion para iniciar sesion con google
    fun iniciarSesionGoogle(credential: AuthCredential, perfil: () -> Unit) = viewModelScope.launch {
        try {
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.let {
                            guardarDatosUsuario( it.email, it.displayName)
                        }
                        Log.d("Login", "Logeado con google")
                        perfil()
                    }
                }
                .addOnFailureListener {
                    Log.d("Login", "Error al logear con google")
                }
        } catch (ex: Exception) {
            Log.d("Login", "Error al logear con google")
        }
    }
    //Registramos los datos del usuario en firebase
    private fun guardarDatosUsuario( email: String?, displayName: String?) {

        val user = firebaseAuth.currentUser
        if (user != null) {

            val db = FirebaseFirestore.getInstance()
            val usuarioId = user.uid
            val datosUsuario = hashMapOf(
                "usuarioId" to usuarioId,
                "email" to email,
                "usuario" to displayName,
                "fecha De Nacimiento" to null,
            )
            db.collection("usuario")
                .add(datosUsuario)
                .addOnSuccessListener {
                    Log.d("Login", "Datos del usuario guardados correctamente")
                }
                .addOnFailureListener {
                    Log.d("Login", "Error al guardar los datos del usuario")
                }
        }
    }
}



