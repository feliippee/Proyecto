package com.example.nutricionydeportefr.pantallas.perfil

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.nutricionydeportefr.scaffold.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Perfil(navController: NavController, perfilViewModel: PerfilViewModel, scaffoldViewModel: ScaffoldViewModel) {
    Scaffold(
        topBar = { Toolbar(scaffoldViewModel, navController) },
        bottomBar = { BottomMenu(navController, perfilViewModel) }
    ) {


    }
}



@Composable
fun BottomMenu(navController: NavController, perfilViewModel: PerfilViewModel) {

    val opcionBottonMenu: Int by perfilViewModel.opcionBottonMenu.observeAsState(initial = 3)

    BottomNavigation(
        backgroundColor = Color(0xFF46B62D),
        contentColor = Color.Black
    ) {
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
            label = { androidx.compose.material.Text(text = "Ejercicios") },
            selected = opcionBottonMenu == 1,
            onClick = {
                perfilViewModel.setOpcionBottonMenu(1)
                navController.navigate("ejercicios")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FoodBank, contentDescription = "Dietas") },
            label = { androidx.compose.material.Text("Dietas") },
            selected = opcionBottonMenu == 2,
            onClick = {
                perfilViewModel.setOpcionBottonMenu(2)
                navController.navigate("alimentacion")
            }
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
