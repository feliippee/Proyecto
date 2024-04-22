package com.example.nutricionydeportefr.pantallas

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
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
            //Creamos una variable mutable para guardar la contraseña que introduce el usuario
            var contrasena by remember { mutableStateOf("") }
            OutlinedTextField(
                //Vinculamos el valor del campo de texto con la variable contrasena
                value = contrasena,
                //Cuando el usuario introduce la contraseña, se guarda en la variable contrasena
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                //Si mostrarContrasenas es true, se muestra la contraseña, si no, se oculta
                visualTransformation = if (mostrarContrasenas) VisualTransformation.None else PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(10.dp))

            // OutlinedTextfield para confirmar la contraseña del usuario
            var Confirmarcontrasena by remember { mutableStateOf("") }
            var errorConfirmarContraseña by remember { mutableStateOf(false) }
            OutlinedTextField(

                value = Confirmarcontrasena,
                onValueChange = { Confirmarcontrasena = it },
                label = { Text("Confirmar Contraseña") },
                visualTransformation = if (mostrarContrasenas) VisualTransformation.None else PasswordVisualTransformation(),
                //Si las contraseñas no coinciden, se muestra un mensaje de error
                isError = errorConfirmarContraseña,
            )
            if (errorConfirmarContraseña) {
                Text(
                    text = "Las contraseñas no coinciden",
                    color = Color.Red
                )
            }
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
            var correvalido by remember { mutableStateOf(false) }
            OutlinedTextField(value = correo,
                onValueChange = {
                    correo = it
                    correvalido = !validarCorreo(correo)

                },
                label = { Text("Correo") },
                isError = correvalido
                )
            if (correvalido) {
                Text(
                    text = "Correo no valido",
                    color = Color.Red
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Spacer(modifier = Modifier.height(10.dp))
            //Boton de registro
            Button(onClick = {
                //Comprobamos si las contraseñas coinciden
                if (contrasena != Confirmarcontrasena) {
                    errorConfirmarContraseña = true
                } else {
                    errorConfirmarContraseña = false
                }
                //Comprobamos si el correo es valido
                if (validarCorreo(correo)) {
                    correvalido = false
                } else {
                    correvalido = true
                }
            }) {
                Text(text = "Registrarse")
            }
        }
    }
}
//Funcion para validar el correo
fun validarCorreo(correo: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
}

@Preview(showBackground = true)
@Composable
fun RegistroPreview() {
    NutricionYDeporteFRTheme {
        Registro(navController = NavController(LocalContext.current))
    }
}

