package com.example.nutricionydeportefr.pantallas.sport

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.*
import androidx.compose.material3.Text
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
import com.example.nutricionydeportefr.scaffold.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Sport(navController: NavController, sportViewModel: SportViewModel, scaffoldViewModel: ScaffoldViewModel) {

    val entrenamientos by sportViewModel.entrenamientos.observeAsState(initial = emptyList())
    val cargaDatosEntrenamiento by sportViewModel.cargaDatos.observeAsState(initial = true)

    Scaffold(
        topBar = { Toolbar(scaffoldViewModel, navController) },
        bottomBar = { BottomMenu(navController, sportViewModel) },
        floatingActionButton = { ActionFloatingButton(navController) }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            if (cargaDatosEntrenamiento) {
                ProgressBar()
            } else {
                Body(
                    Modifier
                        .align(Alignment.TopStart),
                    entrenamientos,
                    sportViewModel
                )
            }
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
        if (entrenamientos.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "No hay entrenamientos registrados",
                    color = Color.LightGray,
                    fontSize = 20.sp
                )
            }
        } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(entrenamientos) { itemEntrenamiento ->
                        Itementreno(itemEntrenamiento = itemEntrenamiento, sportViewModel)
                    }
                    item { Spacer(modifier = Modifier.height(56.dp)) }
                }

        }
    }
}

@Composable
fun Itementreno(itemEntrenamiento: ItemEntrenamiento, sportViewModel: SportViewModel) {


    var expandir by remember { mutableStateOf(false) }
    var mostrarDialog by remember { mutableStateOf(false) }

    if (mostrarDialog) {
        AlertDialogEntreno(
            onDismissRequest = { mostrarDialog = false },
            onConfirm = {
                sportViewModel.deleteEntrenamiento(itemEntrenamiento)
                mostrarDialog = false
            },
            onDismiss = { mostrarDialog = false }
        )
    }
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp)
            .clickable { expandir = !expandir },
        border = BorderStroke(2.dp, Color(0xFF56C63D)),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
    ) {
        Box {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {

                Text(
                    text = "Fecha: ${itemEntrenamiento.fecha}",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 4.dp),
                    fontWeight = FontWeight.Bold,
                )
                Text(text = "Parte del Cuerpo: ${itemEntrenamiento.parteCuerpo}")
                if (expandir) {
                    Text(text = "Ejercicios: ${itemEntrenamiento.ejercicios}")
                    Text(text = "Series: ${itemEntrenamiento.series}")
                    Text(text = "Repeticiones: ${itemEntrenamiento.repeticiones}")
                    val pesoInicialFormateado = String.format("%.2f", itemEntrenamiento.pesoInicial.toDouble())
                    Text(text = "Peso Inicial: $pesoInicialFormateado")
                    val pesoFinalFormateado = String.format("%.2f", itemEntrenamiento.pesoFinal.toDouble())
                    Text(text = "Peso Final: $pesoFinalFormateado")
                }
            }
            IconButton(
                onClick = {
                    mostrarDialog = true
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
            }


        }
    }
}

@Composable
fun AlertDialogEntreno(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = { Text("¿Estás seguro que deseas borrar este entreno?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun BottomMenu(navController: NavController, sportViewModel: SportViewModel) {
    val opcionBottonMenu: Int by sportViewModel.opcionBottonMenu.observeAsState(initial = 1)

    BottomNavigation(
        backgroundColor = Color(0xFF56C63D),

        ) {
       /* BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { androidx.compose.material.Text(text = "Home") },

            selected = opcionBottonMenu == 0,
            onClick = {
                sportViewModel.setOpcionBottonMenu(0)
                navController.navigate("home")
            }
        )*/
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
            navController.navigate("registrosport")
        },
        backgroundColor = Color(0xFF56C63D),
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }

}

