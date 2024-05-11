package com.example.nutricionydeportefr.pantallas.home

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.ui.theme.NutricionYDeporteFRTheme


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController) {
    Scaffold(
        topBar = { Toolbar() },
        bottomBar = { BottomMenu() }
    ){

    }
}

@Composable
fun Toolbar() {
    TopAppBar(
        title = { Text(text = "NutriSport") },
        backgroundColor = Color(0xFF46B62D),

        )
}
@Composable
fun BottomMenu(){
    BottomNavigation(
        backgroundColor = Color(0xFF46B62D),
        contentColor = Color.White
    ){
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selectedContentColor = Color.Black,
            selected = true,
            onClick = { /*TODO*/ }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FitnessCenter, contentDescription = "Ejercicios") },
            label = { Text("Ejercicios") },
            selectedContentColor = Color.Black,
            selected = false,
            onClick = { /*TODO*/ }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FoodBank, contentDescription = "Alimentos") },
            label = { Text("Alimentos") },
            selectedContentColor = Color.Black,
            selected = false,
            onClick = { /*TODO*/ }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selectedContentColor = Color.Black,

            selected = false,
            onClick = { /*TODO*/ }
        )
    }
}