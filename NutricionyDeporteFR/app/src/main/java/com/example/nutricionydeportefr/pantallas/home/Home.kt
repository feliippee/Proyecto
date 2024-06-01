package com.example.nutricionydeportefr.pantallas.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.pantallas.registroDieta.RegistroDietaViewModel
import com.example.nutricionydeportefr.scaffold.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController, homeViewModel: HomeViewModel, scaffoldViewModel: ScaffoldViewModel) {

    Scaffold(
        topBar = { Toolbar(scaffoldViewModel, navController) },
        bottomBar = { BottomMenu(navController, homeViewModel) }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Body(Modifier.align(Alignment.TopStart), homeViewModel)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Body(modifier: Modifier, homeViewModel: HomeViewModel) {
    Column(modifier = modifier) {
        Titulo()
        Spacer(modifier = Modifier.height(8.dp))
        ObjetivoMarcado(homeViewModel)
    }

}

@Composable
fun Titulo() {
    Text(
        text = "¿Que objetivo deseas lograr?",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 8.dp, top = 16.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjetivoMarcado(homeViewModel: HomeViewModel) {

    val objetivoMarcado by homeViewModel.objetivoMarcado.observeAsState(initial = "Pulsa para seleccionar un objetivo")
    val expandir by homeViewModel.expandir.observeAsState(initial = false)

    val opcionesObjetivo = listOf("Perder peso", "Ganar peso", "Mantener peso")
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

    ) {
        //OutlinedTextField y dropdownmenu con las opciones
        OutlinedTextField(
            value = objetivoMarcado,
            onValueChange = { homeViewModel.setObjetivoMarcado(it) },
            readOnly = true,
            maxLines = 1,
            enabled = false,
            modifier = Modifier
                .clickable { homeViewModel.setDesplegable() },
            colors = TextFieldDefaults.textFieldColors(disabledTextColor = Color.Black),
        )
        DropdownMenu(
            expanded = expandir,
            onDismissRequest = { homeViewModel.setDesplegable() })
        {
            opcionesObjetivo.forEach { opcion ->
                DropdownMenuItem(onClick = {
                    homeViewModel.setObjetivoMarcado(opcion)
                    homeViewModel.setDesplegable()
                }) {
                    Text(text = opcion)
                }
            }
        }
    }

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

