package com.example.nutricionydeportefr.pantallas

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.widget.DatePicker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.nutricionydeportefr.ui.theme.NutricionYDeporteFRTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registro(navController: NavController) {
    val context = LocalContext.current
    var fechaNacimiento by remember { mutableStateOf(LocalDate.now()) }
    var dialogoFechaAbierto by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // OutlinedTextfield para registrar el nombre del usuario
            var usuario by remember { mutableStateOf("") }
            OutlinedTextField(value = usuario,
                onValueChange = {
                    usuario = it
                },
                label = { Text("Nombre de usuario") })

            Spacer(modifier = Modifier.height(10.dp))

            //Variable para el checkbox de mostrar contraseñas
            var mostrarContrasenas by remember { mutableStateOf(false) }

            // OutlinedTextfield para registrar la contraseña del usuario
            var contrasena by remember { mutableStateOf("") }
            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                visualTransformation = if (mostrarContrasenas) VisualTransformation.None else PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(10.dp))

            // OutlinedTextfield para confirmar la contraseña del usuario
            var Confirmarcontrasena by remember { mutableStateOf("") }
            OutlinedTextField(
                value = Confirmarcontrasena,
                onValueChange = { Confirmarcontrasena = it },
                label = { Text("Confirmar Contraseña") },
                visualTransformation = if (mostrarContrasenas) VisualTransformation.None else PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Checkbox para mostrar/ocultar contraseñas
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = mostrarContrasenas,
                    onCheckedChange = { mostrarContrasenas = it }
                )
                Text("Mostrar contraseña")
            }
            Spacer(modifier = Modifier.height(10.dp))
            //OutlinedTextfield para registrar el correo del usuario
            var correo by remember { mutableStateOf("") }
            OutlinedTextField(value = correo,
                onValueChange = {
                    correo = it
                },
                label = { Text("Correo") })
            Spacer(modifier = Modifier.height(10.dp))

            //OutlinedTextfield y cuando clique mostramos la funcion de fechaNacimiento
            OutlinedTextField(
                value = SimpleDateFormat("dd/MM/yyyy").format(fechaNacimiento),
                onValueChange = {},
                label = { Text("Fecha de nacimiento") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {
                        dialogoFechaAbierto = true
                    }) {
                        Icon(painter = painterResource(id = R.drawable.calendario), contentDescription = "Fecha de nacimiento")
                    }
                }
            )

            //
            Spacer(modifier = Modifier.height(10.dp))
            //Boton de registro
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Registrarse")
            }
        }
    }
}
//Funcion para mostrar el dialogo de fecha
fun FechaNacimiento(context: android.content.Context, fechaNacimiento: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val calendar = Calendar.getInstance()
    val dialog = DatePickerDialog(context, { _: DatePicker?, year: Int, month: Int, day: Int ->
        val fecha = LocalDate.of(year, month + 1, day)
        onDateSelected(fecha)
    }, fechaNacimiento.year, fechaNacimiento.monthValue - 1, fechaNacimiento.dayOfMonth)
    dialog.show()
}
