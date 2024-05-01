package com.example.nutricionydeportefr.pantallas.login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.SignInPassword
import com.google.firebase.auth.FirebaseAuth

lateinit var firebaseAuth: FirebaseAuth
const val RC_SIGN_IN = 123
class LoginViewModel: ViewModel() {
    //Variable para el email, aqui se modifica
    private val _email = MutableLiveData<String>()
    //Variable para email, esta no modifica el valor, sino que accede a la variale Mutable y la modifica
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _mostrarpassword = MutableLiveData<Boolean>(false)
    val mostrartpassword : LiveData<Boolean> = _mostrarpassword
   //Funciones para obtener el valor de los campos y actualizarlos
    fun onEmailChanged(email: String){
        _email.value = email
    }
    fun onPasswordChanged(password: String){
        _password.value = password
    }
    fun onMostrarPasswod(){
        _mostrarpassword.value = _mostrarpassword.value?.not()
    }

    //Funcion para iniciar sesion, comprobando los campos
    //Funcion para comprobar el inicio de sesion
     fun comprobarInicioSesion(
        email: String,
        password: String,
        context: android.content.Context,
        navController: NavController
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    //Mostramos un mensaje y llamamos al menu
                    Toast.makeText(context, "Inicio de sesion correcto", Toast.LENGTH_SHORT).show()
                    navController.navigate("home")
                } else {
                    //Si no se ha podido iniciar sesion mostramos un mensaje
                    Toast.makeText(context, "Error al iniciar sesion, comprueba los datos", Toast.LENGTH_SHORT).show()
                }
            }
    }
}