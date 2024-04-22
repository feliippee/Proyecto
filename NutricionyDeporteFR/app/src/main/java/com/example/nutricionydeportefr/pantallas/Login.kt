package com.example.nutricionydeportefr.pantallas

import android.content.res.Configuration
import com.example.nutricionydeportefr.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.navegacion.Escenas
import com.example.nutricionydeportefr.ui.theme.NutricionYDeporteFRTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController) {
    //Variables
    var showSnackBar by remember { mutableStateOf(false) }
    //LazyColumn es un composable que permite desplazarse verticalmente
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Nutricion y Deporte FR",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            //Añadir espacio entre campos
            Spacer(modifier = Modifier.height(10.dp))
            //Imagen
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            var usuario by remember { mutableStateOf("") }
            OutlinedTextField(value = usuario,
                onValueChange = {
                    usuario = it
                },
                label = { Text("Nombre de usuario") })
            Spacer(modifier = Modifier.height(10.dp))
            var contrasena by remember { mutableStateOf("") }
            var mostrarContrasena by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                visualTransformation = if (mostrarContrasena) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { mostrarContrasena = !mostrarContrasena }) {
                        Icon(
                            painter = painterResource(id = if (mostrarContrasena) R.drawable.mostrar_password else R.drawable.ocultar_password),
                            contentDescription = if (mostrarContrasena) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(125.dp))
            Button(onClick = { /*TODO*/
                showSnackBar = true
            }) {
                Text(text = "Iniciar Sesión")
            }
            if (showSnackBar) {
                Snackbar(
                    modifier = Modifier.padding(12.dp),
                    action = {
                        Button(onClick = { showSnackBar = false }) {
                            Text("Cerrar")
                        }
                    }
                ) {
                    Text("Usuario o contraseña incorrectos")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { /*TODO*/ },
                //Poner el fondo del boton en blanco
                // colors = ButtonDefaults.buttonColors(MaterialTheme)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Iniciar Sesión con Google",
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = "Facebook",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Iniciar Sesión con Facebook",
                    )
                }
            }

        }
    }
    LazyRow(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 365.dp)

    ) {
        item {
            Button(
                onClick = {
                          navController.navigate(route = Escenas.Registro.ruta)
                          },
                modifier = Modifier.padding(12.dp)
            )
            {
                Text(text = "Registro")
            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(12.dp)) {
                Text(text = "Recuperar Contraseña")
            }
        }

    }
}


