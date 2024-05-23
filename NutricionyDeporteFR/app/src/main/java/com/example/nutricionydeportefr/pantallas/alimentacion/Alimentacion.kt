package com.example.nutricionydeportefr.pantallas.alimentacion

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.nutricionydeportefr.scaffold.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Alimentacion(navController: NavController, alimentacionViewModel: AlimentacionViewModel, scaffoldViewModel: ScaffoldViewModel) {
    Scaffold(
        topBar = { Toolbar(scaffoldViewModel, navController) },
        bottomBar = { BottomMenu(navController,alimentacionViewModel) },
        floatingActionButton = { ActionFloatingButton(navController)}
    ){

    }
}


@Composable
fun BottomMenu(navController: NavController, alimentacionViewModel: AlimentacionViewModel){
   val opcionBottonMenu: Int by alimentacionViewModel.opcionBottonMenu.observeAsState(initial = 2)

    BottomNavigation(
        backgroundColor = Color(0xFF46B62D),
        contentColor = Color.Black
    ){
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text(text = "Home") },
            selected = opcionBottonMenu == 0,
            onClick = {
                alimentacionViewModel.setOpcionBottonMenu(0)
                navController.navigate("home")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FitnessCenter, contentDescription = "Ejercicios") },
            label = { Text(text = "Ejercicios") },
            selected = opcionBottonMenu == 1,
            onClick = {
                alimentacionViewModel.setOpcionBottonMenu(1)
                navController.navigate("ejercicios")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FoodBank, contentDescription = "Dietas") },
            label = { Text(text = "Dietas") },
            selected = opcionBottonMenu == 2,
            onClick = {
                alimentacionViewModel.setOpcionBottonMenu(2)
                navController.navigate("alimentacion")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text(text = "Perfil") },
            selected = opcionBottonMenu == 3,
            onClick = {
                alimentacionViewModel.setOpcionBottonMenu(3)
                navController.navigate("perfil")
            }
        )
    }
}

@Composable
fun ActionFloatingButton(navController: NavController) {
    FloatingActionButton(
        onClick = {
            navController.navigate("registroDieta")
        },
        backgroundColor = Color(0xFF46B62D),
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }

}