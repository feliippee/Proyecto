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
        RacionLFH(registroDietaViewModel)
        Spacer(modifier = Modifier.size(12.dp))
        RacionPGV(registroDietaViewModel)
        Spacer(modifier = Modifier.size(12.dp))
        Suplementacion(registroDietaViewModel)
        Spacer(modifier = Modifier.size(12.dp))
        BtnRegistrarAlimentacion(navController, registroDietaViewModel)

    }
}

@Composable
fun Titulo() {
    Text(
        text = "Registrar Alimentacion",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun CampoFecha(registroDietaViewModel: RegistroDietaViewModel) {
    //Variable para obtener el contexto de la pantalla actual
    val context = LocalContext.current

    //Variable para obtener y guardar la fecha de la dieta del viewmodel
    val fechaDieta by registroDietaViewModel.fechaDieta.observeAsState(initial = "")

    //Variable para mostrar error de la fecha de la dieta del viewmodel
    val fechaDietaError: String? by registroDietaViewModel.fechaError.observeAsState(initial = null)

    // Textfield para registrar la fecha de nacimiento del usuario
    TextField(
        value = fechaDieta,
        onValueChange = { registroDietaViewModel.onfechaDietaChanged(it) },
        label = { Text(text = "Fecha Alimentacion") },
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
                contentDescription = "Fecha Alimentacion",
                tint = Color.Black,
                modifier = Modifier.clickable {
                    registroDietaViewModel.fechaDialog(context, calendar) { fechaSeleccionada ->
                        registroDietaViewModel.onfechaDietaChanged(fechaSeleccionada)
                    }
                }
            )
        }
    )
}

@Composable
fun CampoComida(registroDietaViewModel: RegistroDietaViewModel) {

    //Variables para guardar la comida seleccionada en viewModel
    val comidaSeleccionada by registroDietaViewModel.comidaseleccionada.observeAsState(initial = "")

    //Variable para mostrar error de la comida seleccionada
    val comidaSeleccionadaError: String? by registroDietaViewModel.comidaSeleccionadaError.observeAsState(initial = null)

    //Variable para expandir el dropdown
    val expandir by registroDietaViewModel.expandir.observeAsState(initial = false)

    //Lista de comidas
    val comidas = listOf("Desayuno", "Almuerzo", "Comida", "Merienda", "Cena")

    Column {
        TextField(
            value = comidaSeleccionada,
            onValueChange = { registroDietaViewModel.onComidaChange(it) },
            label = { Text(text = "Tipo de Alimentacion") },
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
fun Menu(registroDietaViewModel: RegistroDietaViewModel) {

    val menu by registroDietaViewModel.menu.observeAsState(initial = "")
    val menuError: String? by registroDietaViewModel.menuError.observeAsState(initial = null)

    // Textfield para registrar el menu del usuario
    TextField(
        value = menu,
        onValueChange = { registroDietaViewModel.onMenuChange(it) },
        label = { Text(text = "Platos") },
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
fun RacionLFH(registroDietaViewModel: RegistroDietaViewModel) {

    //Variables para guardar y obtener datos en el viewModel
    val racionLacteo by registroDietaViewModel.racionLacteo.observeAsState(initial = "")
    val racionFruta by registroDietaViewModel.racionFruta.observeAsState(initial = "")
    val racionHidratos by registroDietaViewModel.racionHidratos.observeAsState(initial = "")

    //Variables para mostrar error en los campos
    val racionLacteoError by registroDietaViewModel.racionLacteoError.observeAsState(initial = null)
    val racionFrutaError by registroDietaViewModel.racionFrutaError.observeAsState(initial = null)
    val racionHidratosError by registroDietaViewModel.racionHidratosError.observeAsState(initial = null)

    Row {
        //Racion de lacteos
        TextField(
            value = racionLacteo,
            onValueChange = { registroDietaViewModel.onLacteoChange(it) },
            label = { Text(text = "Racion Lacteos", fontSize = 14.sp) },
            maxLines = 1,
            modifier = Modifier.width(85.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = racionLacteoError != null,
            supportingText = {
                racionLacteoError?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Color.Red),
                    )
                }
            },
        )

        Spacer(modifier = Modifier.size(10.dp))

        //Racion de frutas
        TextField(
            value = racionFruta,
            onValueChange = { registroDietaViewModel.onFrutaChange(it) },
            label = { Text(text = "Racion Fruta", fontSize = 14.sp) },
            maxLines = 1,
            modifier = Modifier.width(80.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = racionFrutaError != null,
            supportingText = {
                racionFrutaError?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Color.Red),
                    )
                }
            },
        )

        Spacer(modifier = Modifier.size(15.dp))

        //Racion de hidratos
        TextField(
            value = racionHidratos,
            onValueChange = { registroDietaViewModel.onHidratosChange(it) },
            label = { Text(text = "Racion Hidratos", fontSize = 14.sp) },
            maxLines = 1,
            modifier = Modifier.width(90.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = racionHidratosError != null,
            supportingText = {
                racionHidratosError?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Color.Red),
                    )
                }
            },
        )

    }
}

@Composable
fun RacionPGV(registroDietaViewModel: RegistroDietaViewModel) {

    //Variables para guardar y obtener datos en el viewModel
    val racionProteina by registroDietaViewModel.racionProteina.observeAsState(initial = "")
    val racionGrasa by registroDietaViewModel.racionGrasas.observeAsState(initial = "")
    val racionVerdura by registroDietaViewModel.racionVerduras.observeAsState(initial = "")

    //Variables para mostrar error en los campos
    val racionProteinaError by registroDietaViewModel.racionProteinaError.observeAsState(initial = null)
    val racionGrasaError by registroDietaViewModel.racionGrasasError.observeAsState(initial = null)
    val racionVerduraError by registroDietaViewModel.racionVerduraError.observeAsState(initial = null)

    //Campos de textfield
    Row {
        //Racion de proteina
        TextField(
            value = racionProteina,
            onValueChange = { registroDietaViewModel.onProteinaChange(it) },
            label = { Text(text = "Racion Proteina", fontSize = 14.sp) },
            maxLines = 1,
            modifier = Modifier.width(88.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = racionProteinaError != null,
            supportingText = {
                racionProteinaError?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Color.Red),
                    )
                }
            },
        )

        Spacer(modifier = Modifier.size(10.dp))

        //Racion de grasa
        TextField(
            value = racionGrasa,
            onValueChange = { registroDietaViewModel.onGrasasChange(it) },
            label = { Text(text = "Racion Grasa", fontSize = 14.sp) },
            maxLines = 1,
            modifier = Modifier.width(80.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = racionGrasaError != null,
            supportingText = {
                racionGrasaError?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Color.Red),
                    )
                }
            },
        )

        Spacer(modifier = Modifier.size(10.dp))

        //Racion de verduras
        TextField(
            value = racionVerdura,
            onValueChange = { registroDietaViewModel.onVerduraChange(it) },
            label = { Text(text = "Racion Verduras", fontSize = 14.sp) },
            maxLines = 1,
            modifier = Modifier.width(93.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = racionVerduraError != null,
            supportingText = {
                racionVerduraError?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Color.Red),
                    )
                }
            },
        )
    }
}

@Composable
fun Suplementacion(registroDietaViewModel: RegistroDietaViewModel) {

    //Variables para guardar y obtener datos en el viewModel
    val suplementacion by registroDietaViewModel.suplementacion.observeAsState(initial = "")

    //Variables para mostrar error en los campos
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
fun BtnRegistrarAlimentacion(navController: NavController, registroDietaViewModel: RegistroDietaViewModel) {

    //Variables para guardar y obtener datos en el viewModel
    val fechaDieta by registroDietaViewModel.fechaDieta.observeAsState(initial = "")
    val comidaSeleccionada by registroDietaViewModel.comidaseleccionada.observeAsState(initial = "")
    val menu by registroDietaViewModel.menu.observeAsState(initial = "")
    val racionLacteo by registroDietaViewModel.racionLacteo.observeAsState(initial = "")
    val racionFruta by registroDietaViewModel.racionFruta.observeAsState(initial = "")
    val racionHidratos by registroDietaViewModel.racionHidratos.observeAsState(initial = "")
    val racionProteina by registroDietaViewModel.racionProteina.observeAsState(initial = "")
    val racionGrasa by registroDietaViewModel.racionGrasas.observeAsState(initial = "")
    val racionVerdura by registroDietaViewModel.racionVerduras.observeAsState(initial = "")
    val suplementacion by registroDietaViewModel.suplementacion.observeAsState(initial = "")

    Button(
        onClick = {
            registroDietaViewModel.comprobarCamposDieta(
                fechaDieta,
                comidaSeleccionada,
                menu,
                racionLacteo,
                racionVerdura,
                racionFruta,
                racionHidratos,
                racionGrasa,
                racionProteina,
                suplementacion,
                navController
            )

        },
        modifier = Modifier
            .padding(16.dp)

    ) {
        Text(text = "Registrar Alimentacion")
    }

}