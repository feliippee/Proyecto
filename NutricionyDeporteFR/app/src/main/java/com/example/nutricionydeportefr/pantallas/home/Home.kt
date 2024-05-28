package com.example.nutricionydeportefr.pantallas.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.pantallas.home.*
import com.example.nutricionydeportefr.pantallas.registro.documentoId
import com.example.nutricionydeportefr.scaffold.*
/*
Mostrar raciones diarias

 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController, homeViewModel: HomeViewModel, scaffoldViewModel: ScaffoldViewModel) {


    Scaffold(
        topBar = { Toolbar(scaffoldViewModel, navController) },
        bottomBar = { BottomMenu(navController,homeViewModel) }
    ){
        Cuerpo()

    }
}

@Composable
fun BottomMenu(navController: NavController,homeViewModel: HomeViewModel){
    val opcionBottonMenu: Int by homeViewModel.opcionBottonMenu.observeAsState(initial = 0)

    BottomNavigation(
        backgroundColor = Color(0xFF46B62D),
        contentColor = Color.Black
    ){
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = opcionBottonMenu == 0,
            onClick = {
                homeViewModel.setOpcionBottonMenu(0)
               // navController.navigate("home")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FitnessCenter, contentDescription = "Ejercicios") },
            label = { Text("Ejercicios") },
            selected = opcionBottonMenu == 1,
            onClick = {
                homeViewModel.setOpcionBottonMenu(1)
               navController.navigate("ejercicios")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FoodBank, contentDescription = "Dietas") },
            label = { Text("Dietas") },
            selected = opcionBottonMenu == 2,
            onClick = {
                homeViewModel.setOpcionBottonMenu(2)
                navController.navigate("alimentacion")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = opcionBottonMenu == 3,
            onClick = {
                homeViewModel.setOpcionBottonMenu(3)
                navController.navigate("perfil")
            }
        )
    }
}

@Composable
fun Cuerpo(){
    
    Text(
        text = "Bienvenido a NutriSport",
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
        color = Color(0xFF46B62D)
    )
}