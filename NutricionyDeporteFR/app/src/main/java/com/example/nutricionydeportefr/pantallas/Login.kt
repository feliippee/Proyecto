package com.example.nutricionydeportefr.pantallas

import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nutricionydeportefr.navegacion.Escenas
import com.example.nutricionydeportefr.ui.theme.NutricionYDeporteFRTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

private lateinit var firebaseAuth: FirebaseAuth
private const val RC_SIGN_IN = 123


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController) {
    //Variables
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)


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
                onClick = {
                    iniciarSesionGoogle(googleSignInClient, firebaseAuth, navController, context)
                },

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
                onClick = {


                },
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
//Funcion para iniciar sesion con google
fun iniciarSesionGoogle(
    googleSignInClient: GoogleSignInClient,
    firebaseAuth: FirebaseAuth,
    navController: NavController,
    context: android.content.Context
) {
    val signInIntent = googleSignInClient.signInIntent
    val launcher = context as ActivityResultCaller
    val resultLauncher = launcher.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            manejarResultado(task, firebaseAuth, navController, context)
        }
    }
    resultLauncher.launch(signInIntent)
}

fun manejarResultado(task: Task<GoogleSignInAccount>, firebaseAuth: FirebaseAuth, navController: NavController, context: android.content.Context) {
    try {
        val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
        if (account != null) {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navController.navigate("home")
                        Toast.makeText(context, "Inicio de sesion correcto", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error al iniciar sesion", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    } catch (e: ApiException) {
        Toast.makeText(context, "Error al iniciar sesion", Toast.LENGTH_SHORT).show()
    }
}



