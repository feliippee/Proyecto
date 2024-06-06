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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.scaffold.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController, homeViewModel: HomeViewModel, scaffoldViewModel: ScaffoldViewModel) {

    LaunchedEffect(key1 = true) {
        homeViewModel.getEntrenamientosHoy()
        homeViewModel.getAlimentacionesHoy()
    }

    Scaffold(topBar = { Toolbar(scaffoldViewModel, navController) }, bottomBar = { BottomMenu(navController) }) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Body(Modifier.align(Alignment.TopStart), homeViewModel, navController)
        }
    }
}


@Composable
fun Body(modifier: Modifier, homeViewModel: HomeViewModel, navController: NavController) {

    Column(modifier = modifier) {
        EntrenamientosDiarios(homeViewModel, navController)
        DietasDiarias(homeViewModel, navController)
        ConsejoDelDia()
    }

}


@Composable
fun ConsejoDelDia() {

    val consejos = listOf(
        "Recuerda beber al menos 2 litros de agua al día.\n",
        "Intenta hacer al menos 30 minutos de cardio al día.\n",
        "Asegúrate de dormir al menos 7 horas al día.\n",
        "El descanso es importante, deja un dia a la semana de recuperacion.\n",
        "Intenta moverte cada hora si tu trabajo es sedentario.\n",
        "Estirar antes y despues de un entrenamiento puede evitar lesiones\n",
        "No te saltes ninguna comida, es importante para mantener el metabolismo activo.\n",
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        border = BorderStroke(2.dp, Color(0xFF56C63D)),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Consejo del día",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = consejos.random(), lineHeight = 20.sp, modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )
        }
    }
}

@Composable
fun EntrenamientosDiarios(homeViewModel: HomeViewModel, navController: NavController) {

    val entrenamientosDiarios: Int by homeViewModel.entrenamientosDiarios.observeAsState(initial = 0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { navController.navigate("ejercicios") },
        border = BorderStroke(2.dp, Color(0xFF56C63D)),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Entrenamientos diarios",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )
            when (entrenamientosDiarios) {
                0 -> {
                    Text(
                        text = "Hoy no has registrado ningún entrenamiento, animate a hacerlo!\nClica para registrar un entrenamiento\n",
                        lineHeight = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)

                    )
                }

                1 -> {
                    Text(
                        text = "Hoy has registrado $entrenamientosDiarios entrenamiento\n",
                        lineHeight = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                    )
                }

                else -> {
                    Text(
                        text = "Hoy has registrado $entrenamientosDiarios entrenamientos\n",
                        lineHeight = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                    )

                }
            }
        }
    }
}

@Composable
fun DietasDiarias(homeViewModel: HomeViewModel, navController: NavController) {

    val alimentacionesDiarias: Int by homeViewModel.alimentacionesDiarias.observeAsState(initial = 0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { navController.navigate("alimentacion") },
        border = BorderStroke(2.dp, Color(0xFF56C63D)),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Alimentaciones diarias",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )
            when (alimentacionesDiarias) {
                0 -> {
                    Text(
                        text = "Hoy no has registrado ninguna alimentación, animate a hacerlo!\nClica para registrar una alimentación\n",
                        lineHeight = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)

                    )
                }

                1 -> {
                    Text(
                        text = "Hoy has registrado $alimentacionesDiarias alimentación\n",
                        lineHeight = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                    )
                }

                else -> {
                    Text(
                        text = "Hoy has registrado $alimentacionesDiarias alimentaciones\n",
                        lineHeight = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                    )

                }
            }
        }
    }
}

@Composable
fun BottomMenu(navController: NavController) {


    var opcionBottonMenu by remember { mutableIntStateOf(0) }

    BottomNavigation(
        backgroundColor = Color(0xFF46B62D), contentColor = Color.Black
    ) {
        BottomNavigationItem(icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = opcionBottonMenu == 0,
            onClick = {
                opcionBottonMenu = 0
                navController.navigate("home")
            })
        BottomNavigationItem(icon = { Icon(Icons.Filled.FitnessCenter, contentDescription = "Ejercicios") },
            label = { Text("Ejercicios") },
            selected = opcionBottonMenu == 1,
            onClick = {
                opcionBottonMenu = 1
                navController.navigate("ejercicios")
            })
        BottomNavigationItem(icon = { Icon(Icons.Filled.FoodBank, contentDescription = "Dietas") },
            label = { Text("Dietas") },
            selected = opcionBottonMenu == 2,
            onClick = {
                opcionBottonMenu = 2
                navController.navigate("alimentacion")
            })
        BottomNavigationItem(icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = opcionBottonMenu == 3,
            onClick = {
                opcionBottonMenu = 3
                navController.navigate("perfil")
            })
    }
}

