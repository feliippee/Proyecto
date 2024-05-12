package com.example.nutricionydeportefr.pantallas.sport

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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Sport(navController: NavController, sportViewModel: SportViewModel) {
    Scaffold(
        bottomBar = { BottomMenu(navController, sportViewModel) }
    ) {

    }
}


@Composable
fun BottomMenu(navController: NavController, sportViewModel: SportViewModel) {
    val opcionBottonMenu: Int by sportViewModel.opcionBottonMenu.observeAsState(initial = 1)

    BottomNavigation(
        backgroundColor = Color(0xFF46B62D),
        contentColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { androidx.compose.material.Text(text = "Home") },
            selected = opcionBottonMenu == 0,
            onClick = {
                sportViewModel.setOpcionBottonMenu(0)
                navController.navigate("home")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FitnessCenter, contentDescription = "Ejercicios") },
            label = { androidx.compose.material.Text("Ejercicios") },
            selected = opcionBottonMenu == 1,
            onClick = {
                sportViewModel.setOpcionBottonMenu(1)
                navController.navigate("ejercicios")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FoodBank, contentDescription = "Dietas") },
            label = { androidx.compose.material.Text("Dietas") },
            selected = opcionBottonMenu == 2,
            onClick = {
                sportViewModel.setOpcionBottonMenu(2)
                navController.navigate("alimentacion")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { androidx.compose.material.Text("Perfil") },
            selected = opcionBottonMenu == 3,
            onClick = {
                sportViewModel.setOpcionBottonMenu(3)
                navController.navigate("perfil")
            }
        )
    }
}