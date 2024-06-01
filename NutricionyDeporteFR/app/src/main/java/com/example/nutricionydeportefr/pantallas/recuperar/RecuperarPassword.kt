package com.example.nutricionydeportefr.pantallas.recuperar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RecuperarPassword(navController: NavController, recuperarPasswordViewModel: RecuperarPasswordViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Cuerpo(Modifier.align(Alignment.TopCenter), recuperarPasswordViewModel, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cuerpo(modifier: Modifier, recuperarPasswordViewModel: RecuperarPasswordViewModel, navController: NavController) {
    val emailError: String? by recuperarPasswordViewModel.errorEmail.observeAsState(initial = null)
    val showDialog by recuperarPasswordViewModel.showDialog.observeAsState(false)
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Correo de recuperación enviado") },
            text = { Text("Si el correo existe, recibirá un email en breves.") },
            confirmButton = {
                TextButton(onClick = {
                    recuperarPasswordViewModel.showDialog.value = false
                    navController.navigate("login")
                }) {
                    Text( text = "Aceptar",
                        color = Color.Black,)
                }
            }
        )
    }
    Column(modifier = modifier) {
        //Titulo
        Text(
            modifier = modifier
                .padding(16.dp),
            text = "Crear nueva contraseña",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        val email: String by recuperarPasswordViewModel.email.observeAsState(initial = "")
        //Campo para el correo
        OutlinedTextField(
            value = email,
            onValueChange = {
                recuperarPasswordViewModel.onEmailChanged(it)
            },
            label = { Text("Email de recuperacion") },
            isError = emailError != null,
            supportingText = {
                emailError?.let {
                    Text(
                        text = it,
                        style = TextStyle(color = Color.Red),
                    )
                }
            },
            modifier = modifier
        )
        Spacer(modifier = Modifier.height(32.dp))
        //Boton
        Button(
            onClick = {
                recuperarPasswordViewModel.recuperarPassword(email, navController)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Enviar Email")
        }


    }

}

