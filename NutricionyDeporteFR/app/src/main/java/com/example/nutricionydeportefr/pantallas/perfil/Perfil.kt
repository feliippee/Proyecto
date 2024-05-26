package com.example.nutricionydeportefr.pantallas.perfil

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.nutricionydeportefr.scaffold.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Perfil(navController: NavController, perfilViewModel: PerfilViewModel, scaffoldViewModel: ScaffoldViewModel) {
    LaunchedEffect(key1 = true) {
        perfilViewModel.obtenerNombreUsuario()
    }

    Scaffold(
        topBar = { Toolbar(scaffoldViewModel, navController) },
        bottomBar = { BottomMenu(navController, perfilViewModel) }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            Body(Modifier.align(Alignment.TopStart),perfilViewModel)
        }


    }
}

@Composable
fun Body(modifier : Modifier, perfilViewModel: PerfilViewModel) {

    Column(
        modifier = modifier
    ) {
        NombreUsuario(perfilViewModel)
    }
}



@Composable
fun NombreUsuario(perfilViewModel: PerfilViewModel) {
    val nombreUsuario by perfilViewModel.nombreUsuario.observeAsState(initial = "")

    Text(
        text = "Bienvenido $nombreUsuario",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold
    )
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
