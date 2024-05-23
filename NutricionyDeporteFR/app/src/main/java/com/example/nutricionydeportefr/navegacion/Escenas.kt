package com.example.nutricionydeportefr.navegacion

//Se hace sealed(sellada)
sealed class Escenas(val ruta: String) {
    //Definimos el objeto con las rutas de las pantallas, para navegar solo entre las que tenemos aqui creadas
    object  Login : Escenas("login")
    object Registro : Escenas("registro")
    object Home : Escenas("home")
    object RecuperarPassword : Escenas("recuperarPassword")
    object Perfil : Escenas("perfil")
    object Alimentacion : Escenas("alimentacion")
    object Ejercicios : Escenas("ejercicios")
    object SplashScreen : Escenas("splashScreen")
    object RegistroSport : Escenas("registroSport")
    object RegistroDieta: Escenas("registroDieta")
    object Carga: Escenas("progressBar")
}