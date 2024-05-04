package com.example.nutricionydeportefr.pantallas.registro

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.google.firebase.Firebase
import com.google.firebase.auth.*
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*




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
        CamposTextos(registroViewModel)
        Spacer(modifier = Modifier.height(50.dp))
        BotonRegistro(context, navController, registroViewModel)
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
fun CamposTextos(registroViewModel: RegistroViewModel) {
    val context = LocalContext.current
    //Variables
    val usuario:String by registroViewModel.usuario.observeAsState(initial = "")
    val password by registroViewModel.password.observeAsState(initial = "")
    val mostrarPassword by registroViewModel.mostrarpassword.observeAsState(initial = false)
    val confirmarPassword by registroViewModel.confirmarPassword.observeAsState(initial = "")
    val errorConfirmarPassword by registroViewModel.errorConfirmarPassword.observeAsState(initial = false)
    val correo by registroViewModel.correo.observeAsState(initial = "")
    val correvalido by registroViewModel.correoValido.observeAsState(initial = false)
    val fechaNacimiento by registroViewModel.fechaNacimiento.observeAsState(initial = "")
    val fechaNacimientoDialog by registroViewModel.fechaNacimientoDialog.observeAsState(initial = false)

    // OutlinedTextfield para registrar el nombre del usuario
    OutlinedTextField(value = usuario,
        onValueChange = {
            registroViewModel.onUsuarioChanged(it)
        },
        label = { Text("Nombre de usuario") })

    Spacer(modifier = Modifier.height(20.dp))

    // OutlinedTextfield para registrar la contraseña del usuario
    OutlinedTextField(
        //Vinculamos el valor del campo de texto con la variable contrasena
        value = password,
        //Cuando el usuario introduce la contraseña, se guarda en la variable contrasena
        onValueChange = { registroViewModel.onPasswordChanged(it) },
        label = { Text("Contraseña") },
        //Si mostrarContrasenas es true, se muestra la contraseña, si no, se oculta
        visualTransformation = if (mostrarPassword) VisualTransformation.None else PasswordVisualTransformation()

    )
    Spacer(modifier = Modifier.height(20.dp))

    // OutlinedTextfield para confirmar la contraseña del usuario
    OutlinedTextField(

        value = confirmarPassword,
        onValueChange = { registroViewModel.onConfirmarPasswordChanged(it) },
        label = { Text("Confirmar Contraseña") },
        visualTransformation = if (mostrarPassword) VisualTransformation.None else PasswordVisualTransformation(),
        //Si las contraseñas no coinciden, se muestra un mensaje de error
        isError = errorConfirmarPassword,
    )
    if (errorConfirmarPassword) {
        Text(
            text = "Las contraseñas no coinciden",
            color = Color.Red
        )
    }
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
    OutlinedTextField(
        value = correo,
        onValueChange = {
           registroViewModel.onCorreoChanged(it)
            registroViewModel.updateCorreoValido(!registroViewModel.validarCorreo(correo))

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

    Spacer(modifier = Modifier.height(20.dp))
    // OutlinedTextfield para registrar la fecha de nacimiento del usuario
    OutlinedTextField(
        value = fechaNacimiento,
        onValueChange = { registroViewModel.onFechaNacimientoChanged(it) },
        label = { Text("Fecha de nacimiento") },
        readOnly = true,
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

}

@Composable
fun BotonRegistro(context: android.content.Context, navController: NavController, registroViewModel: RegistroViewModel) {
    //Variables
    val usuario:String by registroViewModel.usuario.observeAsState(initial = "")
    val password by registroViewModel.password.observeAsState(initial = "")
    val confirmarPassword by registroViewModel.confirmarPassword.observeAsState(initial = "")
    val correo by registroViewModel.correo.observeAsState(initial = "")
    val correvalido by registroViewModel.correoValido.observeAsState(initial = false)
    val fechaNacimiento by registroViewModel.fechaNacimiento.observeAsState(initial = "")

    //Boton de registro
    Button(onClick = {
        //Comprobamos si los campos estan vacios
        if (usuario.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty() || correo.isEmpty() || fechaNacimiento.isEmpty()) {
            //Muestro error en los campos
            Toast.makeText(context, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
        } else {
            //Comprobamos si las contraseñas coinciden y si el email es valido.
            if (password == confirmarPassword && !correvalido) {
                registroViewModel.registrarUsuario(usuario, password, correo, fechaNacimiento, context, navController)

            } else {
                registroViewModel.updateErrorConfirmarPassword(true)
            }
        }
    }) {
        Text(text = "Registrarse")
    }
}


