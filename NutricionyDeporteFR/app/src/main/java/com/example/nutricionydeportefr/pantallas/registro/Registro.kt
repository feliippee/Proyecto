package com.example.nutricionydeportefr.pantallas.registro

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.R
import com.google.firebase.auth.*


@Composable
fun Registro(navController: NavController, registroViewModel: RegistroViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Textotitulo()
        Spacer(modifier = Modifier.height(50.dp))
        Body(registroViewModel, navController)
        Spacer(modifier = Modifier.height(50.dp))

    }
}

@Composable
fun Textotitulo() {

    Text(
        text = "Crear Cuenta",
        style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Body(registroViewModel: RegistroViewModel, navController: NavController) {

    //Obtengo el contexto de la pantalla actual
    val context = LocalContext.current

    //Variables para obtener y escribir datos en el ViewModel
    val usuario: String by registroViewModel.usuario.observeAsState(initial = "")
    val password by registroViewModel.password.observeAsState(initial = "")
    val confirmarPassword by registroViewModel.confirmarPassword.observeAsState(initial = "")
    val mostrarPassword by registroViewModel.mostrarpassword.observeAsState(initial = false)
    val mostrarConfirmarPassword by registroViewModel.mostrarConfirmarpassword.observeAsState(initial = false)
    val correo by registroViewModel.correo.observeAsState(initial = "")
    val fechaNacimiento by registroViewModel.fechaNacimiento.observeAsState(initial = "")

    //Variables para mostrar errores en los campos de texto
    val usuarioError: String? by registroViewModel.usuarioError.observeAsState(initial = null)
    val passwordError: String? by registroViewModel.passwordError.observeAsState(initial = null)
    val correoError: String? by registroViewModel.emailError.observeAsState(initial = null)
    val confirmarPasswordError: String? by registroViewModel.confirmarPasswordError.observeAsState(initial = null)
    val fechaNacimientoError: String? by registroViewModel.fechaError.observeAsState(initial = null)

    // OutlinedTextfield para registrar el nombre del usuario
    OutlinedTextField(value = usuario,
        onValueChange = {
            registroViewModel.onUsuarioChanged(it)
        },
        label = { Text("Nombre de usuario") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        isError = usuarioError != null,
        supportingText = {
            usuarioError?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Red),
                )
            }
        }
    )

    Spacer(modifier = Modifier.height(20.dp))

    // OutlinedTextfield para registrar la contraseña del usuario
    OutlinedTextField(
        value = password,
        onValueChange = { registroViewModel.onPasswordChanged(it) },
        label = { Text("Contraseña") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (mostrarPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {
                registroViewModel.onMostrarPasswod()
            }) {
                Icon(
                    painter = painterResource(id = if (mostrarPassword) R.drawable.mostrar_password else R.drawable.ocultar_password),
                    contentDescription = if (mostrarPassword) "Ocultar contraseña" else "Mostrar contraseña",
                    tint = Color.Black
                )
            }
        },
        isError = passwordError != null,
        supportingText = {
            passwordError?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Red),
                )
            }
        },

        )

    Spacer(modifier = Modifier.height(20.dp))

    // OutlinedTextfield para confirmar la contraseña del usuario
    OutlinedTextField(
        value = confirmarPassword,
        onValueChange = { registroViewModel.onConfirmarPasswordChanged(it) },
        label = { Text("Confirmar Contraseña") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (mostrarConfirmarPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {
                registroViewModel.onMostrarConfirmarPasswod()
            }) {
                Icon(
                    painter = painterResource(id = if (mostrarConfirmarPassword) R.drawable.mostrar_password else R.drawable.ocultar_password),
                    contentDescription = if (mostrarConfirmarPassword) "Ocultar contraseña" else "Mostrar contraseña",
                    tint = Color.Black
                )
            }
        },
        isError = confirmarPasswordError != null,
        supportingText = {
            confirmarPasswordError?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Red),
                )
            }
        }
    )

    Spacer(modifier = Modifier.height(20.dp))

    // OutlinedTextfield para registrar el correo del usuario
    OutlinedTextField(
        value = correo,
        onValueChange = {
            registroViewModel.onCorreoChanged(it)
        },
        label = { Text("Correo") },
        maxLines = 1,
        singleLine = true,
        isError = correoError != null,
        supportingText = {
            correoError?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Red),
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

    Spacer(modifier = Modifier.height(20.dp))

    // OutlinedTextfield para registrar la fecha de nacimiento del usuario
    OutlinedTextField(
        value = fechaNacimiento,
        onValueChange = { registroViewModel.onFechaNacimientoChanged(it) },
        label = { Text("Fecha de nacimiento") },
        maxLines = 1,
        readOnly = true,
        isError = fechaNacimientoError != null,
        supportingText = {
            fechaNacimientoError?.let {
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
                    registroViewModel.fechaDialog(context, calendar) { fechaSeleccionada ->
                        registroViewModel.onFechaNacimientoChanged(fechaSeleccionada)
                    }
                }
            )
        }
    )

    Spacer(modifier = Modifier.height(35.dp))

    Button(
        onClick = {
            registroViewModel.compobarCampos(
                usuario,
                password,
                confirmarPassword,
                correo,
                fechaNacimiento,
                context,
                navController
            )
        },
    ) {
        Text(text = "Registrarse")
    }
}



