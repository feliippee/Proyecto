package com.example.nutricionydeportefr.pantallas

import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
import com.google.firebase.auth.FirebaseAuth
private lateinit var firebaseAuth: FirebaseAuth
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController) {
    //Variables
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) }
    val context = LocalContext.current

    //LazyColumn es un composable que permite desplazarse verticalmente
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            //Instanciamos firebase
            firebaseAuth = FirebaseAuth.getInstance()
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

            OutlinedTextField(value = correo,
                onValueChange = {
                    correo = it
                },
                label = { Text("Correo") })
            Spacer(modifier = Modifier.height(10.dp))

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
            Button(onClick = {
                //Comprobamos que los campos no esten vacios
                if (correo.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(context, "Introduce el Correo y Contraseña para Iniciar Sesion", Toast.LENGTH_SHORT).show()
                    return@Button
                } else {
                    //Comprobamos el inicio de sesion
                    comprobarInicioSesion(correo, contrasena, context, navController)
                }

            }) {
                Text(text = "Iniciar Sesión")
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
                Text(text = "Registrate")
            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(12.dp)) {
                Text(text = "Recuperar Contraseña")
            }
        }

    }
}
//Funcion para comprobar el inicio de sesion
private fun comprobarInicioSesion(
    email: String,
    password: String,
    context: android.content.Context,
    navController: NavController
) {
    firebaseAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                //Mostramos un mensaje y llamamos al menu
                Toast.makeText(context, "Inicio de sesion correcto", Toast.LENGTH_SHORT).show()
                navController.navigate("home")
            } else {
                //Si no se ha podido iniciar sesion mostramos un mensaje
                Toast.makeText(context, "Error al iniciar sesion, comprueba los datos", Toast.LENGTH_SHORT).show()
            }
        }
}


