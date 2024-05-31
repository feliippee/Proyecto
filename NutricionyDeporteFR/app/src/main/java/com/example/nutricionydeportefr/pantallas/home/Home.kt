package com.example.nutricionydeportefr.pantallas.home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.itemsRecycler.ItemAlimentacion
import com.example.nutricionydeportefr.scaffold.*

/*
Mostrar raciones diarias

 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController, homeViewModel: HomeViewModel, scaffoldViewModel: ScaffoldViewModel) {

    Scaffold(
        topBar = { Toolbar(scaffoldViewModel, navController) },
        bottomBar = { BottomMenu(navController, homeViewModel) }
    ) {
        Body(Modifier, homeViewModel)
    }
}


@Composable
fun Body(modifier: Modifier ,homeViewModel: HomeViewModel) {


}




@Composable
fun BottomMenu(navController: NavController, homeViewModel: HomeViewModel) {
    val opcionBottonMenu: Int by homeViewModel.opcionBottonMenu.observeAsState(initial = 0)

    BottomNavigation(
        backgroundColor = Color(0xFF46B62D),
        contentColor = Color.Black
    ) {
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

