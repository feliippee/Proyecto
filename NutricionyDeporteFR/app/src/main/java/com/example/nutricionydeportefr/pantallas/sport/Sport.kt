package com.example.nutricionydeportefr.pantallas.sport

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Sport(navController: NavController, sportViewModel: SportViewModel) {

    Scaffold(
        bottomBar = { BottomMenu(navController, sportViewModel) },
        floatingActionButton = { ActionFloatingButton(navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Body()
        }
    }
}

@Composable
fun Body() {
    Box(
        modifier = Modifier.padding(16.dp).fillMaxSize(),
    ) {
        Text(
            text = "Entrenamientos Realizados",
        )
    }
}

@Composable
fun BottomMenu(navController: NavController, sportViewModel: SportViewModel) {
    val opcionBottonMenu: Int by sportViewModel.opcionBottonMenu.observeAsState(initial = 1)

    BottomNavigation(
        backgroundColor = Color(0xFF46B62D),

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

@Composable
fun ActionFloatingButton(navController: NavController) {
    FloatingActionButton(
        onClick = {
            navController.navigate("registroSport")
        },
        backgroundColor = Color(0xFF46B62D)

    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}
