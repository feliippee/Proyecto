package com.example.nutricionydeportefr.pantallas.recuperar

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.nutricionydeportefr.pantallas.login.firebaseAuth
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.auth.auth
import kotlinx.coroutines.*

class RecuperarPasswordViewModel {
    //Variable
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email
    //Variable de error
    private val _errorEmail = MutableLiveData<String>()
    val errorEmail: LiveData<String> = _errorEmail

    //Funciones
    fun onEmailChanged(email: String) {
        _email.value = email
    }
    init {
        email.observeForever {
            _errorEmail.value = null
        }
    }

    //Funcion para enviar correo para recuperar la contraseña
    @OptIn(DelicateCoroutinesApi::class)
    fun recuperarPassword(email: String, navController: NavController) {
        //Compruebo que el campo no este vacio
        if (email.isEmpty() || !validarCorreo(email)) {
            _errorEmail.value = "Correo invalido"
        } else {
            firebaseAuth = Firebase.auth
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //Correo enviado
                        Toast.makeText(Firebase.app.applicationContext, "Correo enviado", Toast.LENGTH_SHORT).show()

                        GlobalScope.launch(Dispatchers.Main) {
                            delay(1500)
                        }
                        navController.navigate("login")
                    } else {
                        //Error
                        Toast.makeText(Firebase.app.applicationContext, "Error al enviar el correo", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }
    fun validarCorreo(correo: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }

}