@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.nutricionydeportefr.pantallas.registroDieta

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.R
import com.example.nutricionydeportefr.pantallas.registro.calendar
import com.example.nutricionydeportefr.pantallas.registrosport.RegistroSportViewModel

@Composable
fun RegistroDieta(navController: NavController, registroDietaViewModel: RegistroDietaViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Body(Modifier.align(Alignment.Center), navController, registroDietaViewModel)
    }
}

@Composable
fun Body(modifier: Modifier, navController: NavController, registroDietaViewModel: RegistroDietaViewModel) {
    Column(modifier = modifier) {
        Titulo()
        Spacer(modifier = Modifier.size(30.dp))
        CampoFecha(registroDietaViewModel)
        Spacer(modifier = Modifier.size(12.dp))
        CampoComida(registroDietaViewModel)
        Spacer(modifier = Modifier.size(12.dp))
        Menu(registroDietaViewModel)
        Spacer(modifier = Modifier.size(12.dp))
        Cantidad(registroDietaViewModel)
        Spacer(modifier = Modifier.size(12.dp))
        Calorias(registroDietaViewModel)
        Spacer(modifier = Modifier.size(12.dp))
        Suplementacion(registroDietaViewModel)
        Spacer(modifier = Modifier.size(12.dp))
        BtnRegistrarAlimentacion(navController, registroDietaViewModel)

    }
}

//Fun titulo
@Composable
fun Titulo() {
    Text(
        text = "Registrar Alimentacion",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

//Fun para registrar dia de entrenamiento
@Composable
fun CampoFecha(registroDietaViewModel: RegistroDietaViewModel) {
    val context = LocalContext.current
    val fechaDieta by registroDietaViewModel.fechaDieta.observeAsState(initial = "")
    val fechaDietaError: String? by registroDietaViewModel.fechaError.observeAsState(initial = null)

    // Textfield para registrar la fecha de nacimiento del usuario
    TextField(
        value = fechaDieta,
        onValueChange = { registroDietaViewModel.onfechaDietaChanged(it) },
        label = { Text(text = "Fecha Entrenamiento") },
        maxLines = 1,
        readOnly = true,
        isError = fechaDietaError != null,
        supportingText = {
            fechaDietaError?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Red),
                )
            }
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.calendario),
                contentDescription = "Fecha de nacimiento",
                tint = Color.Black,
                modifier = Modifier.clickable {
                    registroDietaViewModel.FechaDialog(context, calendar) { fechaSeleccionada ->
                        registroDietaViewModel.onfechaDietaChanged(fechaSeleccionada)
                    }
                }
            )
        }
    )
}

@Composable
fun CampoComida(registroDietaViewModel: RegistroDietaViewModel) {

    val comidaSeleccionada by registroDietaViewModel.comidaseleccionada.observeAsState(initial = "")
    val comidaSeleccionadaError: String? by registroDietaViewModel.comidaSeleccionadaError.observeAsState(initial = null)
    val expandir by registroDietaViewModel.expandir.observeAsState(initial = false)
    val comidas = listOf("Desayuno", "Almuerzo", "Comida", "Merienda", "Cena")

    Column {
        TextField(
            value = comidaSeleccionada,
            onValueChange = { registroDietaViewModel.onComidaChange(it) },
            label = { Text(text = "Elige una Opcion") },
            maxLines = 1,
            readOnly = true,
            enabled = false,
            modifier = Modifier
                .clickable { registroDietaViewModel.setDesplegable() },
            isError = comidaSeleccionadaError != null,
            colors = TextFieldDefaults.textFieldColors(disabledTextColor = Color.Black),
            supportingText = {
                comidaSeleccionadaError?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Color.Red),
                    )
                }
            },
        )
        DropdownMenu(
            expanded = expandir,
            onDismissRequest = { registroDietaViewModel.setDesplegable() },
        )
        {
            comidas.forEach { comida ->
                DropdownMenuItem(onClick = {
                    registroDietaViewModel.setDesplegable()
                    registroDietaViewModel.onComidaChange(comida)
                }) {
                    Text(
                        text = comida,
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                }
            }

        }
    }
}

@Composable
fun Menu(registroDietaViewModel: RegistroDietaViewModel){

    val menu by registroDietaViewModel.menu.observeAsState(initial = "")
    val menuError: String? by registroDietaViewModel.menuError.observeAsState(initial = null)

    // Textfield para registrar el menu del usuario
    TextField(
        value = menu,
        onValueChange = { registroDietaViewModel.onMenuChange(it) },
        label = { Text(text = "Menu") },
        maxLines = 6,
        isError = menuError != null,
        supportingText = {
            menuError?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Red),
                )
            }
        },
    )
}

@Composable
fun Cantidad(registroDietaViewModel: RegistroDietaViewModel) {

    val cantidad by registroDietaViewModel.cantidad.observeAsState(initial = "")
    val cantidadError: String? by registroDietaViewModel.cantidadError.observeAsState(initial = null)

    // Textfield para registrar la cantidad de platos del usuario
    TextField(
        value = cantidad,
        onValueChange = { registroDietaViewModel.onCantidadChange(it) },
        label = { Text(text = "Cantidad de Alimentos") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = cantidadError != null,
        supportingText = {
            cantidadError?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Red),
                )
            }
        },
    )

}

@Composable
fun Calorias(registroDietaViewModel: RegistroDietaViewModel){

        val calorias by registroDietaViewModel.calorias.observeAsState(initial = "")
        val caloriasError: String? by registroDietaViewModel.caloriasError.observeAsState(initial = null)

        // Textfield para registrar la cantidad de platos del usuario
        TextField(
            value = calorias,
            onValueChange = { registroDietaViewModel.onCaloriasChange(it) },
            label = { Text(text = "Calorias") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = caloriasError != null,
            supportingText = {
                caloriasError?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Color.Red),
                    )
                }
            },
        )
}

@Composable
fun Suplementacion(registroDietaViewModel: RegistroDietaViewModel) {

        val suplementacion by registroDietaViewModel.suplementacion.observeAsState(initial = "")
        val suplementacionError: String? by registroDietaViewModel.suplementacionError.observeAsState(initial = null)

        // Textfield para registrar la cantidad de platos del usuario
        TextField(
            value = suplementacion,
            onValueChange = { registroDietaViewModel.onSuplementacionChange(it) },
            label = { Text(text = "Suplementacion") },
            maxLines = 1,
            isError = suplementacionError != null,
            supportingText = {
                suplementacionError?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Color.Red),
                    )
                }
            },
        )
}

@Composable
fun BtnRegistrarAlimentacion(navController: NavController, registroDietaViewModel: RegistroDietaViewModel){

    val context = LocalContext.current
    val fechaDieta by registroDietaViewModel.fechaDieta.observeAsState(initial = "")
    val comidaSeleccionada by registroDietaViewModel.comidaseleccionada.observeAsState(initial = "")
    val menu by registroDietaViewModel.menu.observeAsState(initial = "")
    val calorias by registroDietaViewModel.calorias.observeAsState(initial = "")
    val cantidad by registroDietaViewModel.cantidad.observeAsState(initial = "")
    val suplementacion by registroDietaViewModel.suplementacion.observeAsState(initial = "")

    //Boton para registrar la alimentacion
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            onClick = {

            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center)
        ) {
            Text(text = "Registrar")
        }
    }

}