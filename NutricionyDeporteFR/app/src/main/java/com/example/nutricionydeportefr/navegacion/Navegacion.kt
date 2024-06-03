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
import com.example.nutricionydeportefr.pantallas.splashscreen.*
import com.example.nutricionydeportefr.pantallas.registrosport.*
import com.example.nutricionydeportefr.pantallas.registroDieta.*
import com.example.nutricionydeportefr.pantallas.progressbar.*
import com.example.nutricionydeportefr.scaffold.*

//Funcion para manejar la navegacion entre pantallas
@Composable
fun Navegacion() {

    //Esta variable gestiona el estado de navegacion para poder desplazarnos
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Escenas.SplashScreen.ruta) {
        //Composable donde se define la ruta de la pantalla del login
        composable( route = Escenas.Login.ruta) {
            Login(navController, LoginViewModel())
        }
        //Composable donde se define la ruta de la pantalla del registro
        composable( route = Escenas.Registro.ruta) {
            Registro(navController, RegistroViewModel())
        }
        //Composable donde se define la ruta de la pantalla del home
        composable( route = Escenas.Home.ruta) {
            val homeViewModel = HomeViewModel.getInstance()
            Home(navController, homeViewModel, ScaffoldViewModel())
        }
        //Composable donde se define la ruta de la pantalla del recuperar password
        composable( route = Escenas.RecuperarPassword.ruta) {
            RecuperarPassword(navController, RecuperarPasswordViewModel())
        }
        //Composable donde se define la ruta de la pantalla del perfil
        composable (route = Escenas.Perfil.ruta) {
            val perfilViewModel = PerfilViewModel.getInstance()
            Perfil(navController, perfilViewModel, ScaffoldViewModel())
        }
        //Composable donde se define la ruta de la pantalla del alimentacion
        composable (route = Escenas.Alimentacion.ruta) {
            val alimentacionViewModel = AlimentacionViewModel.getInstance()
            Alimentacion(navController, alimentacionViewModel, ScaffoldViewModel())
        }
        //Composable donde se define la ruta de la pantalla del sport
        composable (route = Escenas.Ejercicios.ruta) {
            val sportViewModel = SportViewModel.getInstance()
            Sport(navController, sportViewModel, ScaffoldViewModel())
        }
        //Composable donde se define la ruta de la pantalla del splashscreen
        composable(route = Escenas.SplashScreen.ruta) {
            SplashScreen(navController, SplashScreenViewModel())
        }
        //Composable donde se define la ruta de la pantalla de registro de entrenamiento
        composable(route = Escenas.RegistroSport.ruta) {
            RegistroSport(navController, RegistroSportViewModel())
        }
        //Composable donde se define la ruta de la pantalla de la progressbar
        composable(route = Escenas.Carga.ruta) {
            ProgressBar()
        }
        //Composable donde se define la ruta de la pantalla del registro de alimentacion
        composable(route = Escenas.RegistroDieta.ruta){
            RegistroDieta(navController, RegistroDietaViewModel())
        }
    }
}
