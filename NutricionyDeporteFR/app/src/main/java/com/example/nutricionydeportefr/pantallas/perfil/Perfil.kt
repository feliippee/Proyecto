package com.example.nutricionydeportefr.pantallas.perfil

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Perfil(navController: NavController, perfilViewModel: PerfilViewModel) {
    Scaffold(
        topBar = { Toolbar(perfilViewModel, navController) },
        bottomBar = { BottomMenu(navController, perfilViewModel) }
    ) {

    }
}

@Composable
fun Toolbar(perfilViewModel: PerfilViewModel, navController: NavController) {
    TopAppBar(
        title = { androidx.compose.material.Text(text = "NutriSport") },
        backgroundColor = Color(0xFF46B62D),
        actions = {
            //Desplegable para que el usuario pueda cerrar sesion
            IconButton(onClick = {
                perfilViewModel.setDesplegable()
            }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Cerrar sesion")
            }
            CerrarSesion(perfilViewModel, navController)

        }

    )
}

@Composable
fun CerrarSesion(perfilViewModel: PerfilViewModel, navController: NavController) {
    val expandir by perfilViewModel.expandir.observeAsState(initial = false)
    val context = LocalContext.current
    DropdownMenu(
        expanded = expandir,
        onDismissRequest = { perfilViewModel.setDesplegable() }
    ) {
        DropdownMenuItem(onClick = {
            perfilViewModel.setDesplegable()
            perfilViewModel.setMostrarDialog()
        }) {
            Text("Cerrar sesion")
        }
    }
    AlertDialog(perfilViewModel, navController)
}

@Composable
fun AlertDialog(perfilViewModel: PerfilViewModel, navController: NavController) {
    val mostrarDialog by perfilViewModel.mostrarDialog.observeAsState(initial = false)
    if (mostrarDialog) {
        AlertDialog(
            onDismissRequest = { perfilViewModel.setMostrarDialog() },
            text = { Text("¿Estas seguro que deseas cerrar sesion?") },
            confirmButton = {
                TextButton(onClick = {
                    perfilViewModel.setMostrarDialog()
                    navController.navigate("login")
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { perfilViewModel.setMostrarDialog() }) {
                    Text("Cancelar")
                }
            }
        )
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