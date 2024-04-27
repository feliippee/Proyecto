package com.example.nutricionydeportefr.pantallas

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.res.Configuration
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registro(navController: NavController) {
    //Variables
    val calendar = Calendar.getInstance()
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mostrarContrasenas by remember { mutableStateOf(false) }
    var confirmarcontrasena by remember { mutableStateOf("") }
    var errorConfirmarContraseña by remember { mutableStateOf(false) }
    var correo by remember { mutableStateOf("") }
    var correvalido by remember { mutableStateOf(false) }
    var fechaNacimiento by remember { mutableStateOf("") }
    var fechaNacimientoDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current


        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Texto de bienvenida
                Text(
                    text = "Crear Cuenta",
                    style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),


                )
                Spacer(modifier = Modifier.height(15.dp))
                // OutlinedTextfield para registrar el nombre del usuario
                OutlinedTextField(value = usuario,
                    onValueChange = {
                        usuario = it
                    },
                    label = { Text("Nombre de usuario") })

                Spacer(modifier = Modifier.height(15.dp))

                // OutlinedTextfield para registrar la contraseña del usuario
                OutlinedTextField(
                    //Vinculamos el valor del campo de texto con la variable contrasena
                    value = contrasena,
                    //Cuando el usuario introduce la contraseña, se guarda en la variable contrasena
                    onValueChange = { contrasena = it },
                    label = { Text("Contraseña") },
                    //Si mostrarContrasenas es true, se muestra la contraseña, si no, se oculta
                    visualTransformation = if (mostrarContrasenas) VisualTransformation.None else PasswordVisualTransformation()

                )
                Spacer(modifier = Modifier.height(15.dp))

                // OutlinedTextfield para confirmar la contraseña del usuario
                OutlinedTextField(

                    value = confirmarcontrasena,
                    onValueChange = { confirmarcontrasena = it },
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
                Spacer(modifier = Modifier.height(15.dp))

                // Checkbox para mostrar/ocultar contraseñas
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = mostrarContrasenas,
                        onCheckedChange = { mostrarContrasenas = it }
                    )
                    Text("Mostrar contraseña")
                }
                Spacer(modifier = Modifier.height(15.dp))

                OutlinedTextField(
                    value = correo,
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
                Spacer(modifier = Modifier.height(15.dp))
                // OutlinedTextfield para registrar la fecha de nacimiento del usuario
                OutlinedTextField(
                    value = fechaNacimiento,
                    onValueChange = { fechaNacimiento = it },
                    label = { Text("Fecha de nacimiento") },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.calendario),
                            contentDescription = "Fecha de nacimiento",
                            modifier = Modifier.clickable {
                                fechaNacimientoDialog = true
                            }
                        )
                    }
                )
                if (fechaNacimientoDialog) {
                    FechaDialog(calendar) {
                        fechaNacimiento = it
                        fechaNacimientoDialog = false
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))

                //Boton de registro
                Button(onClick = {
                    //Comprobamos si los campos estan vacios
                    if (usuario.isEmpty() || contrasena.isEmpty() || confirmarcontrasena.isEmpty() || correo.isEmpty() || fechaNacimiento.isEmpty()) {
                        Toast.makeText(context, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
                    }
                    //Comprobamos si las contraseñas coinciden
                    errorConfirmarContraseña = if (contrasena != confirmarcontrasena) {
                        true
                    } else {
                        false
                    }
                    //Comprobamos si el correo es valido
                    correvalido = if (validarCorreo(correo)) {
                        false
                    } else {
                        true
                    }
                }) {
                    Text(text = "Registrarse")
                }
                //Mostrar mensaje de error si algun campo esta vacio

            }
        }

}

//Funcion para validar el correo
fun validarCorreo(correo: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
}

//Funcion DaterPickerDialog
@Composable
fun FechaDialog(calendar: Calendar, onDateSelected: (String) -> Unit) {
    val fecha = DatePickerDialog(
        LocalContext.current,
        { view: DatePicker, year: Int, month: Int, day: Int ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            formatoFecha(calendar, onDateSelected)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    fecha.datePicker.maxDate = System.currentTimeMillis()
    fecha.show()
}

//Funcion para formato de la fecha
fun formatoFecha(calendar: Calendar, onDateSelected: (String) -> Unit) {
    val fechaformato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val fecha = fechaformato.format(calendar.time)
    onDateSelected(fecha)
}

@Preview(showBackground = true)
@Composable
fun RegistroPreview() {
    NutricionYDeporteFRTheme {
        Registro(navController = NavController(LocalContext.current))
    }
}

