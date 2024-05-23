@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.nutricionydeportefr.pantallas.registroDieta

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
            .padding(16.dp)
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
    val fechDieta by registroDietaViewModel.fechaDieta.observeAsState(initial = "")
    val fechaDietaError: String? by registroDietaViewModel.fechaError.observeAsState(initial = null)

    // Textfield para registrar la fecha de nacimiento del usuario
    TextField(
        value = fechDieta,
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

    TextField(
        value = comidaSeleccionada,
        onValueChange = { registroDietaViewModel.onComidaChange(it) },
        label = { Text(text = "Elige una Opcion") },
        maxLines = 1,
        readOnly = true,
        enabled = false,
        modifier = Modifier
            .clickable { }
            .fillMaxWidth(),
        isError = comidaSeleccionadaError != null,
        supportingText = {
            comidaSeleccionadaError?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Red),
                )
            }
        },
    )
    DropdownMenu(expanded = , onDismissRequest = { /*TODO*/ }) {
        
    }
}