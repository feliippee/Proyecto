package com.example.nutricionydeportefr.pantallas.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.identity.SignInPassword

class LoginViewModel: ViewModel() {
    //Variable para el email, aqui se modifica
    private val _email = MutableLiveData<String>()
    //Variable para email, esta no modifica el valor, sino que accede a la variale Mutable y la modifica
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _mostrarpassword = MutableLiveData<Boolean>(false)
    val mostrartpassword : LiveData<Boolean> = _mostrarpassword
    fun onEmailChanged(email: String){
        _email.value = email
    }
    fun onPasswordChanged(password: String){
        _password.value = password
    }
    fun onMostrarPasswod(){
        _mostrarpassword.value = _mostrarpassword.value?.not()
    }
}