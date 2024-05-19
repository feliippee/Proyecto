package com.example.nutricionydeportefr.pantallas.sport

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.itemsRecycler.ItemEntrenamiento
import com.example.nutricionydeportefr.pantallas.progressbar.ProgressBar
import com.example.nutricionydeportefr.pantallas.registrosport.RegistroSportViewModel
import kotlinx.coroutines.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Sport(navController: NavController, sportViewModel: SportViewModel) {
    val entrenamientos by sportViewModel.entrenamientos.observeAsState(initial = emptyList())
    //val carga by sportViewModel.carga.observeAsState(initial = true)
    Scaffold(
        bottomBar = { BottomMenu(navController, sportViewModel) },
        floatingActionButton = {
            ActionFloatingButton(navController)
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {

                Body(Modifier.align(Alignment.TopStart), entrenamientos, sportViewModel)

        }
    }
}

@Composable
fun Body(modifier: Modifier, entrenamientos: List<ItemEntrenamiento>, sportViewModel: SportViewModel) {
    Column(modifier = modifier) {
        Text(
            text = "Entrenamientos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(entrenamientos) { itemEntrenamiento ->
                Itementreno(itemEntrenamiento = itemEntrenamiento, sportViewModel)
            }
        }
    }
}
@Composable
fun Itementreno(itemEntrenamiento: ItemEntrenamiento, sportViewModel: SportViewModel) {
    val expandir by sportViewModel.expandir.observeAsState(initial = false)
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { sportViewModel.expandir.value = !expandir },
        border = BorderStroke(2.dp, Color(0xFF46B62D)),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,

        ) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Fecha: " + itemEntrenamiento.fecha,
                fontSize = 15.sp,
                modifier = Modifier.padding(bottom = 4.dp),
                fontWeight = FontWeight.Bold,
            )
            Text(text = "Parte del Cuerpo: " + itemEntrenamiento.parteCuerpo)
            if (expandir) {
                Text(text = "Ejercicios: " + itemEntrenamiento.ejercicios)
                Text(text = "Series: " + itemEntrenamiento.series)
                Text(text = "Repeticiones: " + itemEntrenamiento.repeticiones)
                Text(text = "Peso: " + itemEntrenamiento.peso)
            }

        }
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
            GlobalScope.launch(Dispatchers.Main) {
                delay(500)
                navController.navigate("registrosport")
            }
        },
        backgroundColor = Color(0xFF46B62D),
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }

}

