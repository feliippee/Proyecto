package com.example.nutricionydeportefr.pantallas.recuperar

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.nutricionydeportefr.pantallas.login.firebaseAuth
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.auth.auth

class RecuperarPasswordViewModel {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    //Funciones
    fun onEmailChanged(email: String) {
        _email.value = email
    }

    //Funcion para enviar correo para recuperar la contraseña
    fun recuperarPassword(email: String, navController: NavController) {
        //Compruebo que el campo no este vacio
        if (email.isEmpty()) {
            Toast.makeText(Firebase.app.applicationContext, "El campo no puede estar vacio", Toast.LENGTH_SHORT).show()
            return
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(Firebase.app.applicationContext, "El email no es valido", Toast.LENGTH_SHORT).show()
            return
        } else {
            firebaseAuth = Firebase.auth

            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //Correo enviado
                        Toast.makeText(Firebase.app.applicationContext, "Correo enviado", Toast.LENGTH_SHORT).show()
                        navController.navigate("login")
                    } else {
                        //Error
                        Toast.makeText(Firebase.app.applicationContext, "Error al enviar el correo", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

}