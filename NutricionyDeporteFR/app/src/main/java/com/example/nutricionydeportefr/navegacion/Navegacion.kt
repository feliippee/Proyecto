package com.example.nutricionydeportefr.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutricionydeportefr.pantallas.login.Login
import com.example.nutricionydeportefr.pantallas.registro.Registro
import com.example.nutricionydeportefr.pantallas.home.Home
import com.example.nutricionydeportefr.pantallas.login.LoginViewModel

//Aqui es donde vamos a pasar las pantallas y gestiona la navegacion entre estas
@Composable
fun Navegacion() {

    //Esta variable gestiona el estado de navegacion para poder desplazarnos
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Escenas.Login.ruta) {
        composable( route = Escenas.Login.ruta) {
            Login(navController, LoginViewModel())
        }
        composable( route = Escenas.Registro.ruta) {
            Registro(navController)
        }
        composable( route = Escenas.Home.ruta) {
            Home(navController)
        }
    }

}