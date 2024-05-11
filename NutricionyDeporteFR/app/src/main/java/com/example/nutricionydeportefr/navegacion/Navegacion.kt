package com.example.nutricionydeportefr.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutricionydeportefr.pantallas.login.*
import com.example.nutricionydeportefr.pantallas.registro.*
import com.example.nutricionydeportefr.pantallas.recuperar.*
import com.example.nutricionydeportefr.pantallas.home.*
import com.example.nutricionydeportefr.pantallas.perfil.*
import com.example.nutricionydeportefr.pantallas.sport.*
import com.example.nutricionydeportefr.pantallas.alimentacion.*
import com.google.firebase.auth.FirebaseAuth


@Composable
fun Navegacion() {
    val auth = FirebaseAuth.getInstance()
    val usuario = auth.currentUser

    //Si tenemos usuario loguado nos vamos al home y sino al login
    val startDestination = if (usuario == null)  Escenas.Login.ruta  else  Escenas.Home.ruta

    //Esta variable gestiona el estado de navegacion para poder desplazarnos
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable( route = Escenas.Login.ruta) {
            Login(navController, LoginViewModel())
        }
        composable( route = Escenas.Registro.ruta) {
            Registro(navController, RegistroViewModel())
        }
        composable( route = Escenas.Home.ruta) {
            Home(navController, HomeViewModel())
        }
        composable( route = Escenas.RecuperarPassword.ruta) {
            RecuperarPassword(navController, RecuperarPasswordViewModel())
        }
        composable (route = Escenas.Perfil.ruta) {
            Perfil(navController, PerfilViewModel())
        }
        composable (route = Escenas.Alimentacion.ruta) {
            Alimentacion(navController, AlimentacionViewModel())
        }
        composable (route = Escenas.Ejercicios.ruta) {
            Sport(navController, SportViewModel())
        }
    }

}
