package com.example.nutricionydeportefr.pantallas.perfil

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.nutricionydeportefr.pantallas.home.HomeViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Perfil(navController: NavController,perfilViewModel: PerfilViewModel) {
    Scaffold(
        topBar = { Toolbar() },
        bottomBar = { BottomMenu(navController,perfilViewModel) }
    ){

    }
}

@Composable
fun Toolbar() {
    TopAppBar(
        title = { androidx.compose.material.Text(text = "NutriSport") },
        backgroundColor = Color(0xFF46B62D),

        )
}
@Composable
fun BottomMenu(navController: NavController, perfilViewModel: PerfilViewModel){
   val opcionBottonMenu: Int by perfilViewModel.opcionBottonMenu.observeAsState(initial = 3)

    BottomNavigation(
        backgroundColor = Color(0xFF46B62D),
        contentColor = Color.Black
    ){
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { androidx.compose.material.Text("Home") },
            selected = opcionBottonMenu == 0,
            onClick = {
                perfilViewModel.setOpcionBottonMenu(0)
                navController.navigate("home")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FitnessCenter, contentDescription = "Ejercicios") },
            label = { androidx.compose.material.Text("Ejercicios") },
            selected = opcionBottonMenu == 1,
            onClick = { perfilViewModel.setOpcionBottonMenu(1) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FoodBank, contentDescription = "Alimentacion") },
            label = { androidx.compose.material.Text("Alimentacion") },
            selected = opcionBottonMenu == 2,
            onClick = { perfilViewModel.setOpcionBottonMenu(2) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { androidx.compose.material.Text("Perfil") },
            selected = opcionBottonMenu == 3,
            onClick = {
                perfilViewModel.setOpcionBottonMenu(3)
                navController.navigate("perfil")
            }
        )
    }
}