@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.nutricionydeportefr.pantallas.registrosport

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.R
import com.example.nutricionydeportefr.pantallas.registro.calendar

@Composable
fun RegistroSport(navController: NavController, registroSportViewModel: RegistroSportViewModel) {

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Body(Modifier.align(Alignment.Center), navController, registroSportViewModel)
    }
}


@Composable
fun Body(modifier: Modifier, navController: NavController, registroSportViewModel: RegistroSportViewModel) {

    Column(modifier = modifier) {
        Titulo()
        Spacer(modifier = Modifier.size(30.dp))
        CampoFecha(registroSportViewModel)
        Spacer(modifier = Modifier.size(8.dp))
        ParteCuerpo(registroSportViewModel)
        Spacer(modifier = Modifier.size(8.dp))
        Ejercicios(registroSportViewModel)
        Spacer(modifier = Modifier.size(8.dp))
        SeriesRepeticiones(registroSportViewModel)
        Spacer(modifier = Modifier.size(8.dp))
        Pesos(registroSportViewModel)
        Spacer(modifier = Modifier.size(8.dp))
        BtnRegistrarEntreno(navController, registroSportViewModel)
    }
}

@Composable
fun Titulo() {
    Text(
        text = "Registrar Entrenamiento",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}


@Composable
fun CampoFecha(registroSportViewModel: RegistroSportViewModel) {

    //Obtener el contexto de la pantalla actual
    val context = LocalContext.current

    //Variables para guardar y obtener datos en el viewModel
    val fechEntrenamiento by registroSportViewModel.fechaEntrenamiento.observeAsState(initial = "")

    //Variables para mostrar error en los campos
    val fechEntrenamientoError: String? by registroSportViewModel.fechaError.observeAsState(initial = null)

    // Textfield para registrar la fecha de nacimiento del usuario
    TextField(
        value = fechEntrenamiento,
        onValueChange = { registroSportViewModel.onfechaEntrenamientoChanged(it) },
        label = { Text(text = "Fecha Entrenamiento") },
        maxLines = 1,
        readOnly = true,
        isError = fechEntrenamientoError != null,
        supportingText = {
            fechEntrenamientoError?.let {
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
                    registroSportViewModel.fechaDialog(context, calendar) { fechaSeleccionada ->
                        registroSportViewModel.onfechaEntrenamientoChanged(fechaSeleccionada)
                    }
                }
            )
        }
    )
}


@Composable
fun ParteCuerpo(registroSportViewModel: RegistroSportViewModel) {

    //Variables para guardar y obtener datos en el viewModel
    val parteCuerpo by registroSportViewModel.parteCuerpo.observeAsState(initial = "")

    //Variables para mostrar error en los campos
    val parteCuerpoError: String? by registroSportViewModel.parteCuerpoError.observeAsState(initial = null)


    // Textfield para registrar la fecha de nacimiento del usuario
    TextField(
        value = parteCuerpo,
        onValueChange = { registroSportViewModel.onparteCuerpoChanged(it) },
        label = { Text(text = "Parte Del Cuerpo ") },
        maxLines = 1,
        isError = parteCuerpoError != null,
        supportingText = {
            parteCuerpoError?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Red),
                )
            }
        },
    )
}

@Composable
fun Ejercicios(registroSportViewModel: RegistroSportViewModel) {

    //Variables para guardar y obtener datos en el viewModel
    val ejercicios by registroSportViewModel.ejercicios.observeAsState(initial = "")

    //Variables para mostrar error en los campos
    val ejerciciosError: String? by registroSportViewModel.ejerciciosError.observeAsState(initial = null)

    // Textfield para registrar el nombre del entrenamiento
    TextField(
        value = ejercicios,
        onValueChange = { registroSportViewModel.onEjerciciosChanged(it) },
        label = { Text(text = "Ejercicios") },
        maxLines = 5,
        isError = ejerciciosError != null,
        supportingText = {
            ejerciciosError?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Red),
                )
            }
        },
    )
}


@Composable
fun SeriesRepeticiones(registroSportViewModel: RegistroSportViewModel) {

    //Variables para registrar series y repeticiones
    val series by registroSportViewModel.series.observeAsState(initial = "")
    val repeticiones by registroSportViewModel.repeticiones.observeAsState(initial = "")

    //Variables para mostrar error en los campos
    val seriesError: String? by registroSportViewModel.seriesError.observeAsState(initial = null)
    val repeticionesError: String? by registroSportViewModel.repeticionesError.observeAsState(initial = null)


    Row {
        // Textfield para registrar las series del entrenamiento
        TextField(
            value = series,
            onValueChange = { registroSportViewModel.onSeriesChanged(it) },
            label = { Text(text = "Series") },
            maxLines = 1,
            modifier = Modifier.width(100.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = seriesError != null,
            supportingText = {
                seriesError?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Color.Red),
                    )
                }
            },
        )

        Spacer(modifier = Modifier.size(15.dp))

        //TextField para registrar las repeticiones del entrenamiento
        TextField(
            value = repeticiones,
            onValueChange = { registroSportViewModel.onRepeticionesChanged(it) },
            label = { Text(text = "Reps") },
            maxLines = 1,
            modifier = Modifier.width(100.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = repeticionesError != null,
            supportingText = {
                repeticionesError?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Color.Red),
                    )
                }
            },
        )
    }
}

//Fun para pesos
@Composable
fun Pesos(registroSportViewModel: RegistroSportViewModel) {

    //Variables para guardar y obtener datos en el viewModel
    val pesoInicial by registroSportViewModel.pesoInicial.observeAsState(initial = "")
    val pesoInicialError: String? by registroSportViewModel.pesoInicialError.observeAsState(initial = null)

    //Variables para mostrar error en los campos
    val pesoFinal by registroSportViewModel.pesoFinal.observeAsState(initial = "")
    val pesoFinalError: String? by registroSportViewModel.pesoFinalError.observeAsState(initial = null)

    Row {
        TextField(
            value = pesoInicial,
            onValueChange = { registroSportViewModel.onPesoInicialChanged(it) },
            label = { Text(text = "Peso Inicial") },
            maxLines = 1,
            modifier = Modifier.width(100.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = pesoInicialError != null,
            supportingText = {
                pesoInicialError?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Color.Red),
                    )
                }
            },
        )

        Spacer(modifier = Modifier.size(15.dp))

        TextField(
            value = pesoFinal,
            onValueChange = { registroSportViewModel.onPesoFinalChanged(it) },
            label = { Text(text = "Peso Final") },
            maxLines = 1,
            modifier = Modifier.width(100.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = pesoFinalError != null,
            supportingText = {
                pesoFinalError?.let {
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
fun BtnRegistrarEntreno(navController: NavController, registroSportViewModel: RegistroSportViewModel) {

    //Variables para guardar y obtener datos en el viewModel
    val fechEntrenamiento by registroSportViewModel.fechaEntrenamiento.observeAsState(initial = "")
    val parteCuerpo by registroSportViewModel.parteCuerpo.observeAsState(initial = "")
    val ejercicios by registroSportViewModel.ejercicios.observeAsState(initial = "")
    val series by registroSportViewModel.series.observeAsState(initial = "")
    val repeticiones by registroSportViewModel.repeticiones.observeAsState(initial = "")
    val pesoInicial by registroSportViewModel.pesoInicial.observeAsState(initial = "")
    val pesoFinal by registroSportViewModel.pesoFinal.observeAsState(initial = "")

    Button(
        onClick = {
            registroSportViewModel.compobarCamposEntreno(
                fechEntrenamiento,
                parteCuerpo,
                ejercicios,
                series,
                repeticiones,
                pesoInicial,
                pesoFinal,
                navController
            )

        },
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = "Registrar Entrenamiento",
        )
    }

}
