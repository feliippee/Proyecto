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

    val context = LocalContext.current

    //Instanciamos firebase
    firebaseAuth = FirebaseAuth.getInstance()
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
    // Texto de bienvenida
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

    val context = LocalContext.current
    val usuario:String by registroViewModel.usuario.observeAsState(initial = "")
    val password by registroViewModel.password.observeAsState(initial = "")
    val confirmarPassword by registroViewModel.confirmarPassword.observeAsState(initial = "")
    val mostrarPassword by registroViewModel.mostrarpassword.observeAsState(initial = false)
    val correo by registroViewModel.correo.observeAsState(initial = "")
    val fechaNacimiento by registroViewModel.fechaNacimiento.observeAsState(initial = "")

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
        //Vinculamos el valor del campo de texto con la variable contrasena
        value = password,
        //Cuando el usuario introduce la contraseña, se guarda en la variable contrasena
        onValueChange = { registroViewModel.onPasswordChanged(it) },
        label = { Text("Contraseña") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = passwordError != null,
        supportingText = {
            passwordError?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Red),
                )
            }
        },
        //Si mostrarContrasenas es true, se muestra la contraseña, si no, se oculta
        visualTransformation = if (mostrarPassword) VisualTransformation.None else PasswordVisualTransformation()

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
        visualTransformation = if (mostrarPassword) VisualTransformation.None else PasswordVisualTransformation(),
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

    // Checkbox para mostrar/ocultar contraseñas
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = mostrarPassword,
            onCheckedChange = { registroViewModel.onMostrarPasswod() }
        )
        Text("Mostrar contraseña")
    }
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
                modifier = Modifier.clickable {
                    registroViewModel.FechaDialog(context, calendar) { fechaSeleccionada ->
                        registroViewModel.onFechaNacimientoChanged(fechaSeleccionada)
                    }
                }
            )
        }
    )
    Spacer(modifier = Modifier.height(35.dp))
    //Boton de registro
    Button(onClick = {
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



