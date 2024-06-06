package com.example.nutricionydeportefr.pantallas.recuperar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nutricionydeportefr.pantallas.login.firebaseAuth
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RecuperarPasswordViewModel {

    //Variables LiveDatas para el email y el error
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email


    private val _errorEmail = MutableLiveData<String>()
    val errorEmail: LiveData<String> = _errorEmail

    //Variable para mostrar dialogo
    val showDialog = MutableLiveData(false)

    //Funcion para limpiar el error en el campo
    init {
        email.observeForever {
            _errorEmail.value = null
        }
    }

    //Funcion para escribir en el campo email
    fun onEmailChanged(email: String) {
        _email.value = email
    }

    //Funcion para enviar correo para recuperar la contraseña
    fun recuperarPassword(email: String) {
        //Compruebo que el campo no este vacio
        if (email.isEmpty() || !validarCorreo(email)) {
            _errorEmail.value = "Correo invalido"
        } else {
            firebaseAuth = Firebase.auth
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showDialog.value = true
                    } else {
                        Log.d("RecuperarPasswordViewModel", "Error al enviar el correo")
                    }
                }
        }
    }

    //Funcion para validar el formato del correo
    private fun validarCorreo(correo: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }

}