package com.example.nutricionydeportefr.pantallas.alimentacion

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
import com.example.nutricionydeportefr.itemsRecycler.ItemAlimentacion
import com.example.nutricionydeportefr.pantallas.progressbar.ProgressBar
import com.example.nutricionydeportefr.scaffold.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Alimentacion(
    navController: NavController,
    alimentacionViewModel: AlimentacionViewModel,
    scaffoldViewModel: ScaffoldViewModel
) {

    LaunchedEffect(key1 = true) {
        alimentacionViewModel.getAlimentacion()
    }

    //Variables que obtenemos del ViewModel
    val alimentacion by alimentacionViewModel.alimentacion.observeAsState(initial = emptyList())
    val cargaDatosalimentacion by alimentacionViewModel.cargaDatos.observeAsState(initial = true)

    Scaffold(
        topBar = { Toolbar(scaffoldViewModel, navController) },
        bottomBar = { BottomMenu(navController) },
        floatingActionButton = { ActionFloatingButton(navController) }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {

            //Cargamos los datos de alimentacion, mientras muestro progressbar
            if (cargaDatosalimentacion) {
                ProgressBar()
            } else {
                Body(Modifier.align(Alignment.TopStart), alimentacion, alimentacionViewModel)
            }
        }
    }
}

@Composable
fun Body(modifier: Modifier, alimentacion: List<ItemAlimentacion>, alimentacionViewModel: AlimentacionViewModel) {
    Column(modifier = modifier) {
        Text(
            text = "Alimentacion",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        //Sino hemos obtenido ningun item de alimentacion, muestro un texto
        if (alimentacion.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                androidx.compose.material3.Text(
                    text = "No hay dietas registrados",
                    color = Color.LightGray,
                    fontSize = 20.sp
                )
            }
        } else {
            LazyColumn(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(alimentacion) { itemAlimentacion ->
                    Itemalimentacion(itemAlimentacion = itemAlimentacion, alimentacionViewModel)
                }
                //Spacer para que no se vea cortado el ultimo item
                item { Spacer(modifier = Modifier.height(56.dp)) }
            }
        }
    }
}

@Composable
fun Itemalimentacion(itemAlimentacion: ItemAlimentacion, alimentacionViewModel: AlimentacionViewModel) {

    //Variable para manejar el expandir del card
    var expandir by remember { mutableStateOf(false) }
    //Variable para mostrar el alertDialog si quiere borrar
    var mostrarDialog by remember { mutableStateOf(false) }

    //Si clica en el icono de borrar, se muestra
    if (mostrarDialog) {
        AlertDialogAlimentacion(
            onDismissRequest = { mostrarDialog = false },
            onConfirm = {
                alimentacionViewModel.borrarAlimentacion(itemAlimentacion)
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
                    text = "Fecha: ${itemAlimentacion.fecha}",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 4.dp),
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp
                )
                Text(text = "Tipo Comida: ${itemAlimentacion.comida}")
                if (expandir) {
                    Text(text = "Platos: ${itemAlimentacion.menu}", modifier = Modifier.padding(top = 4.dp))
                    Text(text = "Verduras: ${itemAlimentacion.verduras}", modifier = Modifier.padding(top = 4.dp))
                    Text(text = "Lacteos: ${itemAlimentacion.lacteos}", modifier = Modifier.padding(top = 4.dp))
                    Text(text = "Frutas: ${itemAlimentacion.frutas}", modifier = Modifier.padding(top = 4.dp))
                    Text(text = "Hidratos: ${itemAlimentacion.hidratos}", modifier = Modifier.padding(top = 4.dp))
                    Text(text = "Grasas: ${itemAlimentacion.grasas}", modifier = Modifier.padding(top = 4.dp))
                    Text(text = "Proteinas: ${itemAlimentacion.proteinas}", modifier = Modifier.padding(top = 4.dp))
                    Text(
                        text = "Suplementacion: ${itemAlimentacion.suplementacion}",
                        modifier = Modifier.padding(top = 4.dp)
                    )
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
fun AlertDialogAlimentacion(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = { Text("¿Estás seguro que deseas borrar esta alimentacion?") },
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
fun BottomMenu(navController: NavController) {

    var opcionBottonMenu by remember { mutableIntStateOf(2) }

    BottomNavigation(
        backgroundColor = Color(0xFF56C63D),
        contentColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { androidx.compose.material.Text(text = "Home") },
            selected = opcionBottonMenu == 0,
            onClick = {
                opcionBottonMenu = 0
                navController.navigate("home")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FitnessCenter, contentDescription = "Ejercicios") },
            label = { androidx.compose.material.Text(text = "Ejercicios") },
            selected = opcionBottonMenu == 1,
            onClick = {
                opcionBottonMenu = 1
                navController.navigate("ejercicios")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FoodBank, contentDescription = "Dietas") },
            label = { androidx.compose.material.Text(text = "Dietas") },
            selected = opcionBottonMenu == 2,
            onClick = {
                opcionBottonMenu = 2
                navController.navigate("alimentacion")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { androidx.compose.material.Text(text = "Perfil") },
            selected = opcionBottonMenu == 3,
            onClick = {
                opcionBottonMenu = 3
                navController.navigate("perfil")
            }
        )
    }
}

@Composable
fun ActionFloatingButton(navController: NavController) {
    FloatingActionButton(
        onClick = {
            navController.navigate("registroDieta")
        },
        backgroundColor = Color(0xFF56C63D),
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }

}