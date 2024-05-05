package com.example.nutricionydeportefr.pantallas.login



import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
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
    val mostrarpassword : LiveData<Boolean> = _mostrarpassword

    //Variables para mostrar errores en los campos
    private val _emailError = MutableLiveData<String?>()
    val emailError : LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError : LiveData<String?> = _passwordError


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
                    Toast.makeText(context, "Datos erroneos, compr", Toast.LENGTH_SHORT).show()
                }
            }
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
    //Funcion para iniciar sesion
    fun InicioSesion(
        correo:String,
        password: String,
        context: android.content.Context,
        navController: NavController
    ) {
        //Comprobamos que los campos no esten vacios
        if (correo.isEmpty()) {
            _emailError.value = "Correo no puede estar vacio"
        } else if (password.isEmpty()) {
            _passwordError.value = "Contraseña no puede estar vacio"

        } else {
            //Comprobamos el inicio de sesion
            comprobarInicioSesion(correo, password, context, navController)
        }
    }


}