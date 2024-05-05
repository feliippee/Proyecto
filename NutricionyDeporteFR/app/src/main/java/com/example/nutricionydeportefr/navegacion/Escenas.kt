package com.example.nutricionydeportefr.navegacion

//Se hace sealed(sellada)
sealed class Escenas(val ruta: String) {
    //Definimos el objeto con las rutas de las pantallas, para navegar solo entre las que tenemos aqui creadas
    object  Login : Escenas("login")
    object Registro : Escenas("registro")
    object Home : Escenas("home")
    object RecuperarPassword : Escenas("recuperarPassword")
}