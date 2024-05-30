package com.example.nutricionydeportefr.scaffold

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.nutricionydeportefr.pantallas.sport.SportViewModel


@Composable
fun Toolbar(scaffoldViewModel: ScaffoldViewModel, navController: NavController) {
    TopAppBar(
        title = { androidx.compose.material.Text(text = "NutriSport") },
        backgroundColor = Color(0xFF46B62D),
        actions = {
            //Desplegable para que el usuario pueda cerrar sesion
            IconButton(onClick = {
                scaffoldViewModel.setDesplegable()

            }) {
                Icon(Icons.Filled.Settings , contentDescription = "Cerrar sesion")
            }
            CerrarSesion(scaffoldViewModel, navController)

        }

    )
}
@Composable
fun CerrarSesion(scaffoldViewModel: ScaffoldViewModel, navController: NavController) {
    val expandir by scaffoldViewModel.expandir.observeAsState(initial = false)
    DropdownMenu(
        expanded = expandir,
        onDismissRequest = { scaffoldViewModel.setDesplegable() }
    ) {
        DropdownMenuItem(onClick = {
            scaffoldViewModel.setDesplegable()
            scaffoldViewModel.setMostrarDialog()
        }) {
            Text("Cerrar sesion")
        }
    }
    AlertDialog(scaffoldViewModel, navController)
}
@Composable
fun AlertDialog(scaffoldViewModel: ScaffoldViewModel, navController: NavController) {
    val mostrarDialog by scaffoldViewModel.mostrarDialog.observeAsState(initial = false)
    if (mostrarDialog) {
        androidx.compose.material.AlertDialog(
            onDismissRequest = { scaffoldViewModel.setMostrarDialog() },
            text = { Text("¿Estas seguro que deseas cerrar sesion?") },
            confirmButton = {
                TextButton(onClick = {
                    scaffoldViewModel.setMostrarDialog()
                    scaffoldViewModel.cerrarSesion()
                    navController.navigate("login") {
                        popUpTo("home") {
                            inclusive = true
                        }
                    }

                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { scaffoldViewModel.setMostrarDialog() }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
